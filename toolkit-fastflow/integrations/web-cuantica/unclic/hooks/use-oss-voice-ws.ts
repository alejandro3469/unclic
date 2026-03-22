'use client';

import { useCallback, useEffect, useRef, useState } from 'react';

export type WsLogEntry = { t: number; raw: string };

type UseOssVoiceWsOptions = {
  url: string | undefined;
  enabled: boolean;
};

/**
 * WebSocket demo para eventos de voz (en prod: deltas STT o señales de sesión).
 */
export function useOssVoiceWs({ url, enabled }: UseOssVoiceWsOptions) {
  const [connected, setConnected] = useState(false);
  const [log, setLog] = useState<WsLogEntry[]>([]);
  const wsRef = useRef<WebSocket | null>(null);

  const appendLog = useCallback((raw: string) => {
    setLog((prev) => [...prev.slice(-40), { t: Date.now(), raw }]);
  }, []);

  useEffect(() => {
    if (!enabled || !url) {
      setConnected(false);
      return;
    }

    let closed = false;
    const ws = new WebSocket(url);
    wsRef.current = ws;

    ws.onopen = () => {
      if (closed) return;
      setConnected(true);
      appendLog('→ abierto');
    };
    ws.onclose = () => {
      setConnected(false);
      appendLog('→ cerrado');
    };
    ws.onerror = () => {
      appendLog('→ error de socket');
    };
    ws.onmessage = (ev) => {
      appendLog(`← ${typeof ev.data === 'string' ? ev.data : '[binary]'}`);
    };

    return () => {
      closed = true;
      ws.close();
      wsRef.current = null;
    };
  }, [enabled, url, appendLog]);

  const sendJson = useCallback((payload: unknown) => {
    const w = wsRef.current;
    if (!w || w.readyState !== WebSocket.OPEN) return false;
    w.send(JSON.stringify(payload));
    appendLog(`→ ${JSON.stringify(payload)}`);
    return true;
  }, [appendLog]);

  const ping = useCallback(() => sendJson({ type: 'ping' }), [sendJson]);

  const sendTranscript = useCallback(
    (text: string) => sendJson({ type: 'transcript', text }),
    [sendJson]
  );

  return { connected, log, ping, sendTranscript, sendJson };
}
