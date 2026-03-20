'use client';

import {
  type ComponentProps,
  createContext,
  type HTMLProps,
  type ReactNode,
  type RefObject,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useRef,
  useState,
} from 'react';
import * as SliderPrimitive from '@radix-ui/react-slider';
import { Check, Loader2, Pause, Play, Settings } from 'lucide-react';
import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';

enum ReadyState {
  HAVE_NOTHING = 0,
  HAVE_METADATA = 1,
  HAVE_CURRENT_DATA = 2,
  HAVE_FUTURE_DATA = 3,
  HAVE_ENOUGH_DATA = 4,
}

enum NetworkState {
  NETWORK_EMPTY = 0,
  NETWORK_IDLE = 1,
  NETWORK_LOADING = 2,
  NETWORK_NO_SOURCE = 3,
}

function formatTime(seconds: number) {
  const hrs = Math.floor(seconds / 3600);
  const mins = Math.floor((seconds % 3600) / 60);
  const secs = Math.floor(seconds % 60);
  const formattedMins = mins < 10 ? `0${mins}` : mins;
  const formattedSecs = secs < 10 ? `0${secs}` : secs;
  return hrs > 0 ? `${hrs}:${formattedMins}:${formattedSecs}` : `${mins}:${formattedSecs}`;
}

export interface AudioPlayerItem<TData = unknown> {
  id: string | number;
  src: string;
  data?: TData;
}

interface AudioPlayerApi<TData = unknown> {
  ref: RefObject<HTMLAudioElement | null>;
  activeItem: AudioPlayerItem<TData> | null;
  duration: number | undefined;
  error: MediaError | null;
  isPlaying: boolean;
  isBuffering: boolean;
  playbackRate: number;
  isItemActive: (id: string | number | null) => boolean;
  setActiveItem: (item: AudioPlayerItem<TData> | null) => Promise<void>;
  play: (item?: AudioPlayerItem<TData> | null) => Promise<void>;
  pause: () => void;
  seek: (time: number) => void;
  setPlaybackRate: (rate: number) => void;
}

const AudioPlayerContext = createContext<AudioPlayerApi | null>(null);

export function useAudioPlayer<TData = unknown>(): AudioPlayerApi<TData> {
  const api = useContext(AudioPlayerContext) as AudioPlayerApi<TData> | null;
  if (!api) {
    throw new Error('useAudioPlayer cannot be called outside of AudioPlayerProvider');
  }
  return api;
}

const AudioPlayerTimeContext = createContext<number | null>(null);

export function useAudioPlayerTime() {
  const time = useContext(AudioPlayerTimeContext);
  if (time === null) {
    throw new Error('useAudioPlayerTime cannot be called outside of AudioPlayerProvider');
  }
  return time;
}

function useAnimationFrame(callback: (delta: number) => void) {
  const requestRef = useRef<number | null>(null);
  const previousTimeRef = useRef<number | null>(null);
  const callbackRef = useRef(callback);
  useEffect(() => {
    callbackRef.current = callback;
  }, [callback]);
  useEffect(() => {
    const animate = (time: number) => {
      if (previousTimeRef.current !== null) {
        const delta = (time - previousTimeRef.current) / 1000;
        callbackRef.current(delta);
      }
      previousTimeRef.current = time;
      requestRef.current = requestAnimationFrame(animate);
    };
    requestRef.current = requestAnimationFrame(animate);
    return () => {
      if (requestRef.current) cancelAnimationFrame(requestRef.current);
      previousTimeRef.current = null;
    };
  }, []);
}

export function AudioPlayerProvider<TData = unknown>({
  children,
}: {
  children: ReactNode;
}) {
  const audioRef = useRef<HTMLAudioElement | null>(null);
  const itemRef = useRef<AudioPlayerItem<TData> | null>(null);
  const playPromiseRef = useRef<Promise<void> | null>(null);
  const [readyState, setReadyState] = useState(0);
  const [networkState, setNetworkState] = useState(0);
  const [time, setTime] = useState(0);
  const [duration, setDuration] = useState<number | undefined>(undefined);
  const [error, setError] = useState<MediaError | null>(null);
  const [activeItem, _setActiveItem] = useState<AudioPlayerItem<TData> | null>(null);
  const [paused, setPaused] = useState(true);
  const [playbackRate, setPlaybackRateState] = useState(1);

  const setActiveItem = useCallback(async (item: AudioPlayerItem<TData> | null) => {
    if (!audioRef.current) return;
    if (item?.id === itemRef.current?.id) return;
    itemRef.current = item;
    const currentRate = audioRef.current.playbackRate;
    audioRef.current.pause();
    audioRef.current.currentTime = 0;
    if (item === null) {
      audioRef.current.removeAttribute('src');
    } else {
      audioRef.current.src = item.src;
    }
    audioRef.current.load();
    audioRef.current.playbackRate = currentRate;
  }, []);

  const play = useCallback(
    async (item?: AudioPlayerItem<TData> | null) => {
      if (!audioRef.current) return;
      if (playPromiseRef.current) {
        try {
          await playPromiseRef.current;
        } catch {
          // ignore
        }
      }
      if (item === undefined) {
        playPromiseRef.current = audioRef.current.play() ?? null;
        return;
      }
      if (item?.id === activeItem?.id) {
        playPromiseRef.current = audioRef.current.play() ?? null;
        return;
      }
      itemRef.current = item ?? null;
      const currentRate = audioRef.current.playbackRate;
      if (!audioRef.current.paused) audioRef.current.pause();
      audioRef.current.currentTime = 0;
      if (item === null) {
        audioRef.current.removeAttribute('src');
      } else {
        audioRef.current.src = item.src;
      }
      audioRef.current.load();
      audioRef.current.playbackRate = currentRate;
      playPromiseRef.current = audioRef.current.play() ?? null;
    },
    [activeItem]
  );

  const pause = useCallback(async () => {
    if (!audioRef.current) return;
    if (playPromiseRef.current) {
      try {
        await playPromiseRef.current;
      } catch {
        // ignore
      }
    }
    audioRef.current.pause();
    playPromiseRef.current = null;
  }, []);

  const seek = useCallback((t: number) => {
    if (audioRef.current) audioRef.current.currentTime = t;
  }, []);

  const setPlaybackRate = useCallback((rate: number) => {
    if (audioRef.current) {
      audioRef.current.playbackRate = rate;
      setPlaybackRateState(rate);
    }
  }, []);

  const isItemActive = useCallback(
    (id: string | number | null) => activeItem?.id === id,
    [activeItem]
  );

  useAnimationFrame(() => {
    if (audioRef.current) {
      _setActiveItem(itemRef.current);
      setReadyState(audioRef.current.readyState);
      setNetworkState(audioRef.current.networkState);
      setTime(audioRef.current.currentTime);
      setDuration(audioRef.current.duration);
      setPaused(audioRef.current.paused);
      setError(audioRef.current.error);
      setPlaybackRateState(audioRef.current.playbackRate);
    }
  });

  const isPlaying = !paused;
  const isBuffering =
    readyState < ReadyState.HAVE_FUTURE_DATA &&
    networkState === NetworkState.NETWORK_LOADING;

  const api = useMemo<AudioPlayerApi<TData>>(
    () => ({
      ref: audioRef,
      duration,
      error,
      isPlaying,
      isBuffering,
      activeItem,
      playbackRate,
      isItemActive,
      setActiveItem,
      play,
      pause,
      seek,
      setPlaybackRate,
    }),
    [
      duration,
      error,
      isPlaying,
      isBuffering,
      activeItem,
      playbackRate,
      isItemActive,
      setActiveItem,
      play,
      pause,
      seek,
      setPlaybackRate,
    ]
  );

  return (
    <AudioPlayerContext.Provider value={api as AudioPlayerApi}>
      <AudioPlayerTimeContext.Provider value={time}>
        <audio ref={audioRef} className="hidden" />
        {children}
      </AudioPlayerTimeContext.Provider>
    </AudioPlayerContext.Provider>
  );
}

export const AudioPlayerProgress = ({
  ...otherProps
}: Omit<
  ComponentProps<typeof SliderPrimitive.Root>,
  'min' | 'max' | 'value' | 'onValueChange'
>) => {
  const player = useAudioPlayer();
  const time = useAudioPlayerTime();
  const wasPlayingRef = useRef(false);

  return (
    <SliderPrimitive.Root
      value={[player.duration ? (time / player.duration) * 100 : 0]}
      onValueChange={(vals) => {
        if (player.duration != null) player.seek((vals[0] / 100) * player.duration);
      }}
      min={0}
      max={100}
      step={0.1}
      onPointerDown={(e) => {
        wasPlayingRef.current = player.isPlaying;
        player.pause();
        otherProps.onPointerDown?.(e);
      }}
      onPointerUp={(e) => {
        if (wasPlayingRef.current) player.play();
        otherProps.onPointerUp?.(e);
      }}
      className={cn(
        'group/player relative flex h-4 touch-none select-none items-center data-[disabled]:opacity-50',
        otherProps.className
      )}
      onKeyDown={(e) => {
        if (e.key === ' ') {
          e.preventDefault();
          if (!player.isPlaying) player.play();
          else player.pause();
        }
        otherProps.onKeyDown?.(e);
      }}
      disabled={
        player.duration === undefined ||
        !Number.isFinite(player.duration) ||
        Number.isNaN(player.duration)
      }
    >
      <SliderPrimitive.Track className="relative h-1.5 grow rounded-full bg-primary/20">
        <SliderPrimitive.Range className="absolute h-full rounded-full bg-primary" />
      </SliderPrimitive.Track>
      <SliderPrimitive.Thumb className="block h-4 w-4 rounded-full border-2 border-primary bg-background ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none" />
    </SliderPrimitive.Root>
  );
};

export const AudioPlayerTime = ({
  className,
  ...props
}: HTMLProps<HTMLSpanElement>) => {
  const time = useAudioPlayerTime();
  return (
    <span className={cn('tabular-nums', className)} {...props}>
      {formatTime(time)}
    </span>
  );
};

export const AudioPlayerDuration = ({
  className,
  ...props
}: HTMLProps<HTMLSpanElement>) => {
  const player = useAudioPlayer();
  const dur = player.duration;
  return (
    <span className={cn('tabular-nums', className)} {...props}>
      {dur != null && Number.isFinite(dur) && !Number.isNaN(dur)
        ? formatTime(dur)
        : '--:--'}
    </span>
  );
};

function Spinner({ className }: { className?: string }) {
  return <Loader2 className={cn('animate-spin', className)} aria-hidden />;
}

function PlayButton({
  playing,
  onPlayingChange,
  className,
  onClick,
  loading,
  ...props
}: React.ComponentProps<typeof Button> & {
  playing: boolean;
  onPlayingChange: (playing: boolean) => void;
  loading?: boolean;
}) {
  return (
    <Button
      type="button"
      variant="default"
      size="icon"
      className={cn('relative', className)}
      aria-label={playing ? 'Pause' : 'Play'}
      onClick={(e) => {
        onPlayingChange(!playing);
        onClick?.(e);
      }}
      {...props}
    >
      {playing ? <Pause className="size-4" /> : <Play className="size-4" />}
      {loading && (
        <span className="absolute inset-0 flex items-center justify-center bg-inherit">
          <Spinner className="size-4" />
        </span>
      )}
    </Button>
  );
}

export interface AudioPlayerButtonProps<TData = unknown>
  extends React.ComponentProps<typeof Button> {
  item?: AudioPlayerItem<TData>;
}

export function AudioPlayerButton<TData = unknown>({
  item,
  ...props
}: AudioPlayerButtonProps<TData>) {
  const player = useAudioPlayer<TData>();

  if (!item) {
    return (
      <PlayButton
        playing={player.isPlaying}
        onPlayingChange={(v) => (v ? player.play() : player.pause())}
        loading={player.isBuffering && player.isPlaying}
        {...props}
      />
    );
  }

  return (
    <PlayButton
      playing={player.isItemActive(item.id) && player.isPlaying}
      onPlayingChange={(shouldPlay) => {
        if (shouldPlay) player.play(item);
        else player.pause();
      }}
      loading={
        player.isItemActive(item.id) && player.isBuffering && player.isPlaying
      }
      {...props}
    />
  );
}

const PLAYBACK_SPEEDS = [0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2] as const;

export interface AudioPlayerSpeedProps
  extends React.ComponentProps<typeof Button> {
  speeds?: readonly number[];
}

export function AudioPlayerSpeed({
  speeds = PLAYBACK_SPEEDS,
  className,
  variant = 'ghost',
  size = 'icon',
  ...props
}: AudioPlayerSpeedProps) {
  const player = useAudioPlayer();
  const currentSpeed = player.playbackRate;

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant={variant} size={size} className={className} {...props}>
          <Settings className="size-4" aria-hidden />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end">
        {speeds.map((speed) => (
          <DropdownMenuItem
            key={speed}
            onClick={() => player.setPlaybackRate(speed)}
            className="flex items-center justify-between"
          >
            {speed === 1 ? 'Normal' : `${speed}x`}
            {currentSpeed === speed && <Check className="size-4" />}
          </DropdownMenuItem>
        ))}
      </DropdownMenuContent>
    </DropdownMenu>
  );
}

export interface AudioPlayerSpeedButtonGroupProps
  extends Omit<React.HTMLAttributes<HTMLDivElement>, 'children'> {
  speeds?: readonly number[];
}

export function AudioPlayerSpeedButtonGroup({
  speeds = [0.5, 1, 1.5, 2],
  className,
  ...props
}: AudioPlayerSpeedButtonGroupProps) {
  const player = useAudioPlayer();
  const currentSpeed = player.playbackRate;

  return (
    <div className={cn('flex gap-1', className)} {...props}>
      {speeds.map((speed) => (
        <Button
          key={speed}
          variant={currentSpeed === speed ? 'default' : 'outline'}
          size="sm"
          onClick={() => player.setPlaybackRate(speed)}
          className="min-w-[50px] font-mono text-xs"
        >
          {speed}x
        </Button>
      ))}
    </div>
  );
}
