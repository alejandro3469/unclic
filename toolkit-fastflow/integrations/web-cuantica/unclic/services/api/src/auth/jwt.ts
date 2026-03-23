import { SignJWT, jwtVerify } from 'jose';
import type { UserRole } from './types.js';

function getSecret(): Uint8Array {
  const raw = process.env.AUTH_JWT_SECRET?.trim();
  if (!raw || raw.length < 16) {
    throw new Error('AUTH_JWT_SECRET must be set (min 16 chars, 32+ recommended)');
  }
  return new TextEncoder().encode(raw);
}

export type PortalJwtPayload = {
  sub: string;
  email: string;
  role: UserRole;
};

export async function signPortalToken(payload: PortalJwtPayload, expiresIn = '7d'): Promise<string> {
  return new SignJWT({ email: payload.email, role: payload.role })
    .setProtectedHeader({ alg: 'HS256' })
    .setSubject(payload.sub)
    .setIssuedAt()
    .setExpirationTime(expiresIn)
    .sign(getSecret());
}

export async function verifyPortalToken(token: string): Promise<PortalJwtPayload> {
  const { payload } = await jwtVerify(token, getSecret(), { algorithms: ['HS256'] });
  const sub = typeof payload.sub === 'string' ? payload.sub : '';
  const email = typeof payload.email === 'string' ? payload.email : '';
  const role = payload.role === 'admin' || payload.role === 'user' ? payload.role : 'user';
  if (!sub || !email) {
    throw new Error('invalid_token_payload');
  }
  return { sub, email, role };
}
