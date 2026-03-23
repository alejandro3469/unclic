'use client';

import { useCallback, useEffect, useRef, useState } from 'react';
import { Bot, Loader2, Send } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { ollamaChat, type OllamaChatMessage } from '@/lib/ollama-client';
import { getOllamaBaseUrl, getOllamaModel } from '@/lib/ollama-config';
import { ollamaLocal } from '@/lib/copy';

export function OllamaChatPanel() {
  const base = getOllamaBaseUrl();
  const model = getOllamaModel();

  const [input, setInput] = useState('');
  const [messages, setMessages] = useState<OllamaChatMessage[]>([
    {
      role: 'assistant',
      content: ollamaLocal.welcomeMessage,
    },
  ]);
  const messagesRef = useRef(messages);
  useEffect(() => {
    messagesRef.current = messages;
  }, [messages]);

  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState<string | null>(null);

  const send = useCallback(async () => {
    const text = input.trim();
    if (!text || !base || loading) return;
    setInput('');
    setErr(null);
    const userMsg: OllamaChatMessage = { role: 'user', content: text };
    const prev = messagesRef.current;
    const withUser = [...prev, userMsg];
    messagesRef.current = withUser;
    setMessages(withUser);
    setLoading(true);
    try {
      const reply = await ollamaChat(base, model, [
        { role: 'system', content: ollamaLocal.systemPrompt },
        ...withUser,
      ]);
      const withAssistant: OllamaChatMessage[] = [
        ...withUser,
        { role: 'assistant', content: reply },
      ];
      messagesRef.current = withAssistant;
      setMessages(withAssistant);
    } catch (e) {
      const msg = e instanceof Error ? e.message : 'Error';
      setErr(msg);
      const failed: OllamaChatMessage[] = [
        ...withUser,
        { role: 'assistant', content: `${ollamaLocal.errorPrefix} ${msg}` },
      ];
      messagesRef.current = failed;
      setMessages(failed);
    } finally {
      setLoading(false);
    }
  }, [base, loading, model]);

  return (
    <Card className="border-emerald-500/20">
      <CardHeader>
        <div className="flex items-center gap-2 text-sm font-semibold">
          <Bot className="size-4 text-emerald-600 dark:text-emerald-400" aria-hidden />
          {ollamaLocal.panelTitle}
        </div>
        <p className="text-sm text-muted-foreground">{ollamaLocal.panelDescription}</p>
        {!base ? (
          <p className="text-xs text-amber-800 dark:text-amber-200">{ollamaLocal.needEnvHint}</p>
        ) : (
          <p className="text-xs text-muted-foreground">
            {ollamaLocal.modelLinePrefix} <code className="rounded bg-muted px-1">{model}</code>
            {' · '}
            <code className="rounded bg-muted px-1">{base}</code>
          </p>
        )}
      </CardHeader>
      <CardContent className="space-y-3">
        {err && (
          <p className="rounded-md border border-destructive/40 bg-destructive/10 px-3 py-2 text-sm text-destructive">
            {err}
          </p>
        )}
        <div className="max-h-64 space-y-2 overflow-y-auto rounded-lg border border-border bg-muted/20 p-3 text-sm">
          {messages.map((m, i) => (
            <div
              key={i}
              className={
                m.role === 'user'
                  ? 'ml-4 rounded-lg bg-primary/10 px-3 py-2 text-foreground'
                  : 'mr-4 rounded-lg border border-border bg-background px-3 py-2 text-muted-foreground'
              }
            >
              {m.content}
            </div>
          ))}
          {loading && (
            <div className="flex items-center gap-2 text-xs text-muted-foreground">
              <Loader2 className="size-3.5 animate-spin" />
              {ollamaLocal.thinking}
            </div>
          )}
        </div>
        <form
          className="flex gap-2"
          onSubmit={(e) => {
            e.preventDefault();
            void send();
          }}
        >
          <input
            className="min-w-0 flex-1 rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none ring-ring focus:ring-2"
            placeholder={base ? ollamaLocal.inputPlaceholder : ollamaLocal.inputDisabledPlaceholder}
            value={input}
            onChange={(e) => setInput(e.target.value)}
            maxLength={8000}
            disabled={!base || loading}
          />
          <Button type="submit" size="sm" disabled={!base || loading || !input.trim()}>
            {loading ? <Loader2 className="size-4 animate-spin" /> : <Send className="size-4" />}
          </Button>
        </form>
        <p className="text-[11px] leading-snug text-muted-foreground">{ollamaLocal.footnoteSd}</p>
      </CardContent>
    </Card>
  );
}
