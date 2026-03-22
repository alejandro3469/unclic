'use client';

import { useCallback, useEffect, useRef } from 'react';
import dynamic from 'next/dynamic';
import { Headphones } from 'lucide-react';
import { BlockContainer } from '@/components/blocks';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import {
  AudioPlayerButton,
  AudioPlayerDuration,
  AudioPlayerProvider,
  AudioPlayerSpeed,
  AudioPlayerSpeedButtonGroup,
  AudioPlayerProgress,
  AudioPlayerTime,
  useAudioPlayer,
} from '@/components/ui/audio-player';
import { AgentWaveformStrip } from '@/components/media/agent-waveform-strip';
import { OssMediaEnvHint } from '@/components/media/oss-media-env-hint';
import { OssVoicePanel } from '@/components/media/oss-voice-panel';
import { AUDIO_TRACKS } from '@/lib/audio-samples';
import { audio } from '@/lib/copy';

const Orb = dynamic(
  () => import('@/components/ui/orb').then((m) => ({ default: m.Orb })),
  {
    ssr: false,
    loading: () => (
      <div className="aspect-square w-full min-h-[200px] rounded-full bg-muted/50 flex items-center justify-center text-muted-foreground text-sm">
        Cargando…
      </div>
    ),
  }
);

/** Ref se actualiza cada frame cuando hay reproducción; el Orb lo lee en getOutputVolume. */
function usePlaybackVolumeRef(isPlaying: boolean) {
  const ref = useRef(0.2);
  useEffect(() => {
    if (!isPlaying) {
      ref.current = 0.2;
      return;
    }
    let raf = 0;
    const tick = () => {
      const t = Date.now() / 200;
      ref.current = Math.min(1, 0.5 + 0.35 * Math.sin(t));
      raf = requestAnimationFrame(tick);
    };
    raf = requestAnimationFrame(tick);
    return () => cancelAnimationFrame(raf);
  }, [isPlaying]);
  return ref;
}

function AudioSectionContent() {
  const player = useAudioPlayer();
  const outputVolumeRef = usePlaybackVolumeRef(player.isPlaying);

  const getOutputVolume = useCallback(() => outputVolumeRef.current, [outputVolumeRef]);

  return (
    <div className="mt-8 space-y-8">
      <div className="grid gap-8 lg:grid-cols-[minmax(0,320px)_1fr]">
      {/* Orb: reacciona al audio en reproducción (volumen simulado desde estado del reproductor). */}
      <Card className="flex flex-col items-center justify-center overflow-hidden p-6">
        <div className="relative size-40 shrink-0 md:size-52">
          <div className="bg-muted relative h-full w-full rounded-full p-1 shadow-[inset_0_2px_8px_rgba(0,0,0,0.1)] dark:shadow-[inset_0_2px_8px_rgba(0,0,0,0.3)]">
            <div className="bg-background h-full w-full overflow-hidden rounded-full">
              <Orb
                className="h-full w-full min-h-0"
                volumeMode="manual"
                getOutputVolume={getOutputVolume}
                colors={['#CADCFC', '#A0B9D1']}
              />
            </div>
          </div>
        </div>
        <p className="mt-4 text-center text-xs text-muted-foreground">
          {audio.ossMediaCaption}
        </p>
        <p className="mt-1 text-center text-[11px] leading-snug text-muted-foreground/90">
          {audio.ossMediaStackLine}
        </p>
      </Card>

      <div className="flex min-w-0 flex-col gap-6">
        <Card>
          <CardHeader>
            <div className="flex items-center gap-2 text-sm font-medium">
              <Headphones className="size-4" aria-hidden />
              {audio.tracksLabel} ({AUDIO_TRACKS.length})
            </div>
          </CardHeader>
          <CardContent className="space-y-3">
            {AUDIO_TRACKS.map((track) => (
              <div
                key={track.id}
                className="flex items-center gap-3 rounded-lg border border-border p-3"
              >
                <AudioPlayerButton item={track} />
                <span className="flex-1 text-sm font-medium">
                  {track.data.title}
                </span>
              </div>
            ))}
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <p className="text-sm text-muted-foreground">
              {audio.controlsDescription}
            </p>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-center gap-3">
              <AudioPlayerButton />
              <div className="flex flex-1 items-center gap-2 text-sm tabular-nums text-muted-foreground">
                <AudioPlayerTime />
                <span>/</span>
                <AudioPlayerDuration />
              </div>
              <AudioPlayerSpeed />
            </div>
            <AudioPlayerProgress className="w-full" />
            <div className="flex items-center justify-between">
              <AudioPlayerSpeedButtonGroup speeds={[0.5, 1, 1.25, 1.5, 2]} />
            </div>
          </CardContent>
        </Card>
      </div>
      </div>
      <OssVoicePanel />
    </div>
  );
}

export function AudioSection() {
  return (
    <section
      id="audio"
      className="bg-gradient-to-b from-violet-50/50 to-fuchsia-50/30 py-16 dark:from-violet-950/15 md:py-24"
      aria-labelledby="audio-heading"
    >
      <BlockContainer>
        <h2 id="audio-heading" className="text-2xl font-semibold tracking-tight sm:text-3xl md:text-4xl">
          {audio.sectionTitle}
        </h2>
        <p className="mt-2 text-muted-foreground">
          {audio.sectionDescription}
        </p>
        <AgentWaveformStrip />
        <OssMediaEnvHint />

        <AudioPlayerProvider>
          <AudioSectionContent />
        </AudioPlayerProvider>
      </BlockContainer>
    </section>
  );
}
