'use client';

import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { LG } from '@/lib/icons8-liquid-glass';
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
import { OllamaChatPanel } from '@/components/media/ollama-chat-panel';
import { OssVoicePanel } from '@/components/media/oss-voice-panel';
import { AUDIO_TRACKS } from '@/lib/audio-samples';
import { audio } from '@/lib/copy';
import { cn } from '@/lib/utils';

function AudioSectionContent() {
  const player = useAudioPlayer();

  return (
    <div className="mt-8 space-y-8">
      <div className="grid gap-8 lg:grid-cols-[minmax(0,320px)_1fr]">
      {/* Indicador visual: gradiente + pulso cuando suena (sin WebGL). */}
      <Card className="flex flex-col items-center justify-center overflow-hidden p-6">
        <div className="relative size-40 shrink-0 md:size-52">
          <div className="bg-muted relative h-full w-full rounded-full p-1 shadow-[inset_0_2px_8px_rgba(0,0,0,0.1)] dark:shadow-[inset_0_2px_8px_rgba(0,0,0,0.3)]">
            <div className="bg-background h-full w-full overflow-hidden rounded-full">
              <div
                className={cn(
                  'h-full w-full rounded-full bg-gradient-to-br from-sky-200/90 to-primary/45 dark:from-sky-900/50 dark:to-primary/35',
                  player.isPlaying && 'animate-pulse'
                )}
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
              <LiquidGlassIcon slug={LG.headphones} size={20} alt="" className="shrink-0" />
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
      <OllamaChatPanel />
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
        <h2 id="audio-heading" className="text-type-section-title">
          {audio.sectionTitle}
        </h2>
        <p className="text-type-lead mt-2 max-w-3xl">
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
