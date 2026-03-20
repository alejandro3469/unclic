'use client';

import * as React from 'react';
import { useEffect, useMemo, useRef, useState } from 'react';
import { cn } from '@/lib/utils';

export type Frame = number[][];
type MatrixMode = 'default' | 'vu';

interface CellPosition {
  x: number;
  y: number;
}

interface MatrixProps extends React.HTMLAttributes<HTMLDivElement> {
  rows: number;
  cols: number;
  pattern?: Frame;
  frames?: Frame[];
  fps?: number;
  autoplay?: boolean;
  loop?: boolean;
  size?: number;
  gap?: number;
  palette?: { on: string; off: string };
  brightness?: number;
  ariaLabel?: string;
  onFrame?: (index: number) => void;
  mode?: MatrixMode;
  levels?: number[];
}

function clamp(value: number): number {
  return Math.max(0, Math.min(1, value));
}

function ensureFrameSize(frame: Frame, rows: number, cols: number): Frame {
  const result: Frame = [];
  for (let r = 0; r < rows; r++) {
    const row = frame[r] ?? [];
    result.push([]);
    for (let c = 0; c < cols; c++) {
      result[r][c] = row[c] ?? 0;
    }
  }
  return result;
}

function useAnimation(
  frames: Frame[] | undefined,
  options: {
    fps: number;
    autoplay: boolean;
    loop: boolean;
    onFrame?: (index: number) => void;
  }
): { frameIndex: number } {
  const [frameIndex, setFrameIndex] = useState(0);
  const frameIdRef = useRef<number | undefined>(undefined);
  const lastTimeRef = useRef(0);
  const accumulatorRef = useRef(0);

  useEffect(() => {
    if (!frames || frames.length === 0 || !options.autoplay) return;

    const frameInterval = 1000 / options.fps;

    const animate = (currentTime: number) => {
      if (lastTimeRef.current === 0) lastTimeRef.current = currentTime;
      const deltaTime = currentTime - lastTimeRef.current;
      lastTimeRef.current = currentTime;
      accumulatorRef.current += deltaTime;

      if (accumulatorRef.current >= frameInterval) {
        accumulatorRef.current -= frameInterval;
        setFrameIndex((prev) => {
          const next = prev + 1;
          if (next >= frames.length) {
            if (options.loop) {
              options.onFrame?.(0);
              return 0;
            }
            return prev;
          }
          options.onFrame?.(next);
          return next;
        });
      }
      frameIdRef.current = requestAnimationFrame(animate);
    };

    frameIdRef.current = requestAnimationFrame(animate);
    return () => {
      if (frameIdRef.current) cancelAnimationFrame(frameIdRef.current);
    };
  }, [frames, options.fps, options.loop, options.autoplay, options.onFrame]);

  useEffect(() => {
    setFrameIndex(0);
    lastTimeRef.current = 0;
    accumulatorRef.current = 0;
  }, [frames]);

  return { frameIndex };
}

function emptyFrame(rows: number, cols: number): Frame {
  return Array.from({ length: rows }, () => Array(cols).fill(0));
}

function setPixel(frame: Frame, row: number, col: number, value: number): void {
  if (row >= 0 && row < frame.length && col >= 0 && col < (frame[0]?.length ?? 0)) {
    frame[row][col] = value;
  }
}

/** Digit sprites 0–9, 7 rows x 5 cols each */
export const digits: Frame[] = [
  [[0,1,1,1,0],[1,0,0,0,1],[1,0,0,0,1],[1,0,0,0,1],[1,0,0,0,1],[1,0,0,0,1],[0,1,1,1,0]],
  [[0,0,1,0,0],[0,1,1,0,0],[0,0,1,0,0],[0,0,1,0,0],[0,0,1,0,0],[0,0,1,0,0],[0,1,1,1,0]],
  [[0,1,1,1,0],[1,0,0,0,1],[0,0,0,0,1],[0,0,0,1,0],[0,0,1,0,0],[0,1,0,0,0],[1,1,1,1,1]],
  [[0,1,1,1,0],[1,0,0,0,1],[0,0,0,0,1],[0,0,1,1,0],[0,0,0,0,1],[1,0,0,0,1],[0,1,1,1,0]],
  [[0,0,0,1,0],[0,0,1,1,0],[0,1,0,1,0],[1,0,0,1,0],[1,1,1,1,1],[0,0,0,1,0],[0,0,0,1,0]],
  [[1,1,1,1,1],[1,0,0,0,0],[1,1,1,1,0],[0,0,0,0,1],[0,0,0,0,1],[1,0,0,0,1],[0,1,1,1,0]],
  [[0,1,1,1,0],[1,0,0,0,0],[1,1,1,1,0],[1,0,0,0,1],[1,0,0,0,1],[1,0,0,0,1],[0,1,1,1,0]],
  [[1,1,1,1,1],[0,0,0,0,1],[0,0,0,1,0],[0,0,1,0,0],[0,1,0,0,0],[0,1,0,0,0],[0,1,0,0,0]],
  [[0,1,1,1,0],[1,0,0,0,1],[1,0,0,0,1],[0,1,1,1,0],[1,0,0,0,1],[1,0,0,0,1],[0,1,1,1,0]],
  [[0,1,1,1,0],[1,0,0,0,1],[1,0,0,0,1],[0,1,1,1,1],[0,0,0,0,1],[0,0,0,0,1],[0,1,1,1,0]],
];

/** Animated wave (7x7) */
export const wave: Frame[] = (() => {
  const frames: Frame[] = [];
  const rows = 7;
  const cols = 7;
  for (let frame = 0; frame < 24; frame++) {
    const f = emptyFrame(rows, cols);
    const phase = (frame / 24) * Math.PI * 2;
    for (let col = 0; col < cols; col++) {
      const colPhase = (col / cols) * Math.PI * 2;
      const height = Math.sin(phase + colPhase) * 2.5 + 3.5;
      const row = Math.floor(height);
      if (row >= 0 && row < rows) {
        setPixel(f, row, col, 1);
        const frac = height - row;
        if (row > 0) setPixel(f, row - 1, col, 1 - frac);
        if (row < rows - 1) setPixel(f, row + 1, col, frac);
      }
    }
    frames.push(f);
  }
  return frames;
})();

/** Pulsing circle (7x7) */
export const pulse: Frame[] = (() => {
  const frames: Frame[] = [];
  const size = 7;
  const center = 3;
  for (let frame = 0; frame < 16; frame++) {
    const f = emptyFrame(size, size);
    const phase = (frame / 16) * Math.PI * 2;
    const intensity = (Math.sin(phase) + 1) / 2;
    setPixel(f, center, center, 1);
    const radius = Math.floor((1 - intensity) * 3) + 1;
    for (let dy = -radius; dy <= radius; dy++) {
      for (let dx = -radius; dx <= radius; dx++) {
        const dist = Math.sqrt(dx * dx + dy * dy);
        if (Math.abs(dist - radius) < 0.7) {
          setPixel(f, center + dy, center + dx, intensity * 0.6);
        }
      }
    }
    frames.push(f);
  }
  return frames;
})();

/** VU meter frame from level per column */
export function vu(columns: number, levels: number[]): Frame {
  const rows = 7;
  const frame = emptyFrame(rows, columns);
  for (let col = 0; col < Math.min(columns, levels.length); col++) {
    const level = Math.max(0, Math.min(1, levels[col]));
    const height = Math.floor(level * rows);
    for (let row = 0; row < rows; row++) {
      const rowFromBottom = rows - 1 - row;
      if (rowFromBottom < height) {
        const brightness = row < rows * 0.3 ? 1 : row < rows * 0.6 ? 0.8 : 0.6;
        frame[row][col] = brightness;
      }
    }
  }
  return frame;
}

export const Matrix = React.forwardRef<HTMLDivElement, MatrixProps>(
  (
    {
      rows,
      cols,
      pattern,
      frames,
      fps = 12,
      autoplay = true,
      loop = true,
      size = 10,
      gap = 2,
      palette = { on: 'currentColor', off: 'var(--muted-foreground)' },
      brightness = 1,
      ariaLabel,
      onFrame,
      mode = 'default',
      levels,
      className,
      ...props
    },
    ref
  ) => {
    const { frameIndex } = useAnimation(frames, {
      fps,
      autoplay: autoplay && !pattern,
      loop,
      onFrame,
    });

    const currentFrame = useMemo(() => {
      if (mode === 'vu' && levels && levels.length > 0) {
        return ensureFrameSize(vu(cols, levels), rows, cols);
      }
      if (pattern) return ensureFrameSize(pattern, rows, cols);
      if (frames && frames.length > 0) {
        return ensureFrameSize(frames[frameIndex] ?? frames[0], rows, cols);
      }
      return ensureFrameSize([], rows, cols);
    }, [pattern, frames, frameIndex, rows, cols, mode, levels]);

    const cellPositions = useMemo(() => {
      const positions: CellPosition[][] = [];
      for (let row = 0; row < rows; row++) {
        positions[row] = [];
        for (let col = 0; col < cols; col++) {
          positions[row][col] = { x: col * (size + gap), y: row * (size + gap) };
        }
      }
      return positions;
    }, [rows, cols, size, gap]);

    const svgDimensions = useMemo(
      () => ({
        width: cols * (size + gap) - gap,
        height: rows * (size + gap) - gap,
      }),
      [rows, cols, size, gap]
    );

    return (
      <div
        ref={ref}
        className={cn('inline-flex', className)}
        role={ariaLabel ? 'img' : undefined}
        aria-label={ariaLabel}
        {...props}
      >
        <svg
          width={svgDimensions.width}
          height={svgDimensions.height}
          className="overflow-visible"
        >
          <defs>
            <linearGradient id="matrix-pixel-on" x1="0" y1="0" x2="0" y2="1">
              <stop offset="0%" stopColor={palette.on} stopOpacity="1" />
              <stop offset="100%" stopColor={palette.on} stopOpacity="0.85" />
            </linearGradient>
            <linearGradient id="matrix-pixel-off" x1="0" y1="0" x2="0" y2="1">
              <stop offset="0%" stopColor={palette.off} stopOpacity="0.25" />
              <stop offset="100%" stopColor={palette.off} stopOpacity="0.1" />
            </linearGradient>
            <filter id="matrix-glow" x="-20%" y="-20%" width="140%" height="140%">
              <feGaussianBlur stdDeviation="0.5" result="blur" />
              <feMerge>
                <feMergeNode in="blur" />
                <feMergeNode in="SourceGraphic" />
              </feMerge>
            </filter>
          </defs>
          <style>{`
            .matrix-pixel {
              transition: opacity 300ms ease-out, transform 150ms ease-out;
              transform-origin: center;
              transform-box: fill-box;
            }
            .matrix-pixel-active {
              filter: url(#matrix-glow);
            }
          `}</style>
          {currentFrame.map((row, rowIndex) =>
            row.map((value, colIndex) => {
              const pos = cellPositions[rowIndex]?.[colIndex];
              if (!pos) return null;
              const opacity = clamp(brightness * value);
              const isActive = opacity > 0.5;
              const isOn = opacity > 0.05;
              const fill = isOn ? 'url(#matrix-pixel-on)' : 'url(#matrix-pixel-off)';
              const scale = isActive ? 1.1 : 1;
              const radius = (size / 2) * 0.9;
              return (
                <circle
                  key={`${rowIndex}-${colIndex}`}
                  className={isActive ? 'matrix-pixel matrix-pixel-active' : 'matrix-pixel'}
                  cx={pos.x + size / 2}
                  cy={pos.y + size / 2}
                  r={radius}
                  fill={fill}
                  opacity={opacity}
                  style={{ transform: `scale(${scale})` }}
                />
              );
            })
          )}
        </svg>
      </div>
    );
  }
);

Matrix.displayName = 'Matrix';
