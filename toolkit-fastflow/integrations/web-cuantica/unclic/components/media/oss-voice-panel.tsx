'use client';

import { useCallback, useEffect, useRef, useState } from 'react';
import { Loader2, Mic, MicOff, Radio, Square, Volume2 } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { useOssVoiceWs } from '@/hooks/use-oss-voice-ws';
import {
  getOssSttUrl,
  getOssTtsUrl,
  getOssVoiceWsUrl,
} from '@/lib/oss-media';
import { synthesizeWithOssTts, transcribeWithOssStt } from '@/lib/oss-voice-client';
import { ossVoice } from '@/lib/copy';

function pickRecorderMime(): string | undefined {
  const c = [
    'audio/webm;codecs=opus',
    'audio/webm',
    'audio/mp4',
  ];
  for (const m of c) {
    if (typeof MediaRecorder !== 'undefined' && MediaRecorder.isTypeSupported(m)) {
      return m;
    }
  }
  return undefined;
}

export function OssVoicePanel() {
  const sttUrl = getOssSttUrl();
  const ttsUrl = getOssTtsUrl();
  const wsUrl = getOssVoiceWsUrl();

  const [transcript, setTranscript] = useState('');
  const [ttsInput, setTtsInput] = useState('Hola, esto es una prueba de TTS open source.');
  const [busy, setBusy] = useState<'stt' | 'tts' | null>(null);
  const [err, setErr] = useState<string | null>(null);

  const mediaRef = useRef<MediaStream | null>(null);
  const recRef = useRef<MediaRecorder | null>(null);
  const chunksRef = useRef<BlobPart[]>([]);
  const [recording, setRecording] = useState(false);

  const audioRef = useRef<HTMLAudioElement | null>(null);
  const ttsObjectUrlRef = useRef<string | null>(null);

  const [wsEnabled, setWsEnabled] = useState(false);
  const { connected, log, ping, sendTranscript } = useOssVoiceWs({
    url: wsUrl,
    enabled: wsEnabled && Boolean(wsUrl),
  });

  const stopStream = useCallback(() => {
    mediaRef.current?.getTracks().forEach((t) => t.stop());
    mediaRef.current = null;
  }, []);

  useEffect(() => () => stopStream(), [stopStream]);

  useEffect(() => {
    return () => {
      if (ttsObjectUrlRef.current) {
        URL.revokeObjectURL(ttsObjectUrlRef.current);
        ttsObjectUrlRef.current = null;
      }
    };
  }, []);

  const startRecording = useCallback(async () => {
    if (!sttUrl) return;
    setErr(null);
    if (typeof MediaRecorder === 'undefined') {
      setErr(ossVoice.errorRecorder);
      return;
    }
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
      mediaRef.current = stream;
      chunksRef.current = [];
      const mime = pickRecorderMime();
      const rec = mime ? new MediaRecorder(stream, { mimeType: mime }) : new MediaRecorder(stream);
      recRef.current = rec;
      rec.ondataavailable = (e) => {
        if (e.data.size > 0) chunksRef.current.push(e.data);
      };
      rec.onstop = async () => {
        stopStream();
        const blob = new Blob(chunksRef.current, {
          type: rec.mimeType || 'audio/webm',
        });
        chunksRef.current = [];
        recRef.current = null;
        setRecording(false);
        if (blob.size === 0) {
          setErr('Grabación vacía.');
          return;
        }
        setBusy('stt');
        try {
          const ext = blob.type.includes('webm') ? 'webm' : blob.type.includes('mp4') ? 'm4a' : 'audio';
          const out = await transcribeWithOssStt(sttUrl, blob, `mic.${ext}`);
          setTranscript(out.text);
        } catch (e) {
          setErr(e instanceof Error ? e.message : 'STT falló');
        } finally {
          setBusy(null);
        }
      };
      rec.start(200);
      setRecording(true);
    } catch {
      setErr(ossVoice.errorMic);
    }
  }, [sttUrl, stopStream]);

  const stopRecording = useCallback(() => {
    const rec = recRef.current;
    if (rec && rec.state !== 'inactive') {
      rec.stop();
    } else {
      setRecording(false);
      stopStream();
    }
  }, [stopStream]);

  const runTts = useCallback(async () => {
    if (!ttsUrl || !ttsInput.trim()) return;
    setErr(null);
    setBusy('tts');
    try {
      const result = await synthesizeWithOssTts(ttsUrl, ttsInput.trim());
      let url: string;
      if (result.kind === 'url') {
        url = result.url;
      } else {
        if (ttsObjectUrlRef.current) {
          URL.revokeObjectURL(ttsObjectUrlRef.current);
        }
        url = URL.createObjectURL(result.blob);
        ttsObjectUrlRef.current = url;
      }
      const el = audioRef.current;
      if (el) {
        el.src = url;
        el.load();
        await el.play().catch(() => {
          setErr('Reproducción bloqueada; pulsa play manualmente.');
        });
      }
    } catch (e) {
      setErr(e instanceof Error ? e.message : 'TTS falló');
    } finally {
      setBusy(null);
    }
  }, [ttsUrl, ttsInput]);

  const hasAny = Boolean(sttUrl || ttsUrl || wsUrl);

  return (
    <Card className="border-primary/20">
      <CardHeader>
        <div className="flex items-center gap-2 text-sm font-semibold">
          <Volume2 className="size-4" aria-hidden />
          {ossVoice.panelTitle}
        </div>
        <p className="text-sm text-muted-foreground">{ossVoice.panelDescription}</p>
        {!hasAny && (
          <p className="text-xs text-amber-800 dark:text-amber-200">{ossVoice.needEnvHint}</p>
        )}
      </CardHeader>
      <CardContent className="space-y-6">
        {err && (
          <p className="rounded-md border border-destructive/40 bg-destructive/10 px-3 py-2 text-sm text-destructive">
            {err}
          </p>
        )}

        {/* STT */}
        <div className="space-y-2">
          <p className="text-xs font-medium uppercase tracking-wide text-muted-foreground">
            STT (Whisper / …)
          </p>
          <div className="flex flex-wrap items-center gap-2">
            {!sttUrl ? (
              <span className="text-sm text-muted-foreground">{ossVoice.missingStt}</span>
            ) : (
              <>
                <Button
                  type="button"
                  variant={recording ? 'destructive' : 'default'}
                  size="sm"
                  disabled={busy !== null}
                  onClick={() => (recording ? stopRecording() : startRecording())}
                >
                  {busy === 'stt' ? (
                    <Loader2 className="mr-2 size-4 animate-spin" />
                  ) : recording ? (
                    <Square className="mr-2 size-4" />
                  ) : (
                    <Mic className="mr-2 size-4" />
                  )}
                  {busy === 'stt'
                    ? ossVoice.transcribing
                    : recording
                      ? ossVoice.stopAndTranscribe
                      : ossVoice.record}
                </Button>
              </>
            )}
          </div>
          <label className="block text-sm font-medium" htmlFor="oss-transcript">
            {ossVoice.transcriptLabel}
          </label>
          <textarea
            id="oss-transcript"
            className="min-h-[88px] w-full rounded-lg border border-input bg-background px-3 py-2 text-sm text-foreground outline-none ring-ring focus:ring-2"
            value={transcript}
            onChange={(e) => setTranscript(e.target.value)}
            placeholder={sttUrl ? '…' : ossVoice.missingStt}
          />
          <Button
            type="button"
            variant="outline"
            size="sm"
            onClick={() => setTranscript('')}
          >
            {ossVoice.clearTranscript}
          </Button>
        </div>

        {/* TTS */}
        <div className="space-y-2">
          <p className="text-xs font-medium uppercase tracking-wide text-muted-foreground">
            {ossVoice.ttsLabel}
          </p>
          {!ttsUrl ? (
            <span className="text-sm text-muted-foreground">{ossVoice.missingTts}</span>
          ) : (
            <>
              <textarea
                className="min-h-[72px] w-full rounded-lg border border-input bg-background px-3 py-2 text-sm text-foreground outline-none ring-ring focus:ring-2"
                value={ttsInput}
                onChange={(e) => setTtsInput(e.target.value)}
                placeholder={ossVoice.ttsPlaceholder}
              />
              <Button
                type="button"
                size="sm"
                disabled={busy !== null || !ttsInput.trim()}
                onClick={() => void runTts()}
              >
                {busy === 'tts' ? (
                  <Loader2 className="mr-2 size-4 animate-spin" />
                ) : (
                  <Volume2 className="mr-2 size-4" />
                )}
                {busy === 'tts' ? ossVoice.synthesizing : ossVoice.synthesize}
              </Button>
              <p className="text-xs text-muted-foreground">{ossVoice.ttsAudioLabel}</p>
              <audio ref={audioRef} controls className="w-full max-w-md" />
            </>
          )}
        </div>

        {/* WebSocket */}
        <div className="space-y-2 border-t border-border pt-4">
          <p className="flex items-center gap-2 text-xs font-medium uppercase tracking-wide text-muted-foreground">
            <Radio className="size-3.5" />
            {ossVoice.wsTitle}
          </p>
          {!wsUrl ? (
            <span className="text-sm text-muted-foreground">{ossVoice.missingWs}</span>
          ) : (
            <>
              <div className="flex flex-wrap gap-2">
                <Button
                  type="button"
                  variant={wsEnabled ? 'secondary' : 'outline'}
                  size="sm"
                  onClick={() => setWsEnabled((v) => !v)}
                >
                  {wsEnabled ? <MicOff className="mr-2 size-4" /> : <Radio className="mr-2 size-4" />}
                  {wsEnabled ? ossVoice.wsDisable : ossVoice.wsEnable}
                </Button>
                <Button type="button" variant="outline" size="sm" disabled={!connected} onClick={ping}>
                  {ossVoice.wsPing}
                </Button>
                <Button
                  type="button"
                  variant="outline"
                  size="sm"
                  disabled={!connected || !transcript.trim()}
                  onClick={() => sendTranscript(transcript.trim())}
                >
                  {ossVoice.wsPushTranscript}
                </Button>
              </div>
              <p className="text-xs text-muted-foreground">
                {connected ? 'Conectado' : wsEnabled ? 'Conectando…' : 'Desconectado'}
              </p>
              <div className="max-h-32 overflow-y-auto rounded-md border border-border bg-muted/30 p-2 font-mono text-[11px] text-muted-foreground">
                <span className="font-sans text-xs font-medium text-foreground">{ossVoice.wsLog}</span>
                <ul className="mt-1 space-y-0.5">
                  {log.map((row, i) => (
                    <li key={`${row.t}-${i}`}>{row.raw}</li>
                  ))}
                </ul>
              </div>
            </>
          )}
        </div>
      </CardContent>
    </Card>
  );
}
