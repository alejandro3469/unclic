import { existsSync, mkdirSync, readFileSync, renameSync, writeFileSync } from 'node:fs';
import { dirname } from 'node:path';
import { randomBytes, randomUUID } from 'node:crypto';
import type { AuthStoreFile, UserRecord, UserRole } from './types.js';

function defaultPath(): string {
  return process.env.AUTH_STORE_PATH?.trim() || './data/auth-store.json';
}

function loadRaw(path: string): AuthStoreFile {
  if (!existsSync(path)) {
    return { users: [] };
  }
  try {
    const raw = readFileSync(path, 'utf8');
    const parsed = JSON.parse(raw) as AuthStoreFile;
    if (!Array.isArray(parsed.users)) return { users: [] };
    return parsed;
  } catch {
    return { users: [] };
  }
}

function atomicWrite(path: string, data: AuthStoreFile) {
  const dir = dirname(path);
  if (!existsSync(dir)) {
    mkdirSync(dir, { recursive: true });
  }
  const tmp = `${path}.${randomBytes(8).toString('hex')}.tmp`;
  writeFileSync(tmp, JSON.stringify(data, null, 2), 'utf8');
  renameSync(tmp, path);
}

function normalizeEmail(email: string): string {
  return email.trim().toLowerCase();
}

export class AuthStore {
  constructor(private readonly path: string = defaultPath()) {}

  private read(): AuthStoreFile {
    return loadRaw(this.path);
  }

  private write(data: AuthStoreFile) {
    atomicWrite(this.path, data);
  }

  findByEmail(email: string): UserRecord | undefined {
    const n = normalizeEmail(email);
    return this.read().users.find((u) => u.email === n);
  }

  findById(id: string): UserRecord | undefined {
    return this.read().users.find((u) => u.id === id);
  }

  findByVerifyToken(token: string): UserRecord | undefined {
    return this.read().users.find((u) => u.verifyToken === token);
  }

  createUser(input: {
    email: string;
    passwordHash: string;
    emailVerified: boolean;
    role: UserRole;
    verifyToken?: string | null;
    verifyExpires?: number | null;
  }): UserRecord {
    const data = this.read();
    const email = normalizeEmail(input.email);
    if (data.users.some((u) => u.email === email)) {
      throw new Error('email_taken');
    }
    const user: UserRecord = {
      id: randomUUID(),
      email,
      passwordHash: input.passwordHash,
      emailVerified: input.emailVerified,
      verifyToken: input.verifyToken ?? null,
      verifyExpires: input.verifyExpires ?? null,
      role: input.role,
      createdAt: new Date().toISOString(),
    };
    data.users.push(user);
    this.write(data);
    return user;
  }

  updateUser(id: string, patch: Partial<Pick<UserRecord, 'emailVerified' | 'verifyToken' | 'verifyExpires' | 'role'>>) {
    const data = this.read();
    const i = data.users.findIndex((u) => u.id === id);
    if (i < 0) throw new Error('not_found');
    data.users[i] = { ...data.users[i], ...patch };
    this.write(data);
    return data.users[i];
  }

  setVerification(id: string, verified: boolean) {
    return this.updateUser(id, {
      emailVerified: verified,
      verifyToken: null,
      verifyExpires: null,
    });
  }
}

let singleton: AuthStore | null = null;

export function getAuthStore(): AuthStore {
  if (!singleton) singleton = new AuthStore();
  return singleton;
}

/** Solo tests: permite otro `AUTH_STORE_PATH` entre casos. */
export function resetAuthStoreForTests(): void {
  singleton = null;
}

export function newVerifyToken(): string {
  return randomBytes(32).toString('hex');
}
