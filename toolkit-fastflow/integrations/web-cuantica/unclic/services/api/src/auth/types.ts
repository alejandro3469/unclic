export type UserRole = 'admin' | 'user';

export type UserRecord = {
  id: string;
  email: string;
  passwordHash: string;
  emailVerified: boolean;
  verifyToken: string | null;
  verifyExpires: number | null;
  role: UserRole;
  createdAt: string;
};

export type AuthStoreFile = {
  users: UserRecord[];
};
