'use client';

import { Signup10 } from '@/components/signup10';
import { Footer1Section } from '@/components/sections/footer1-section';
import { signupPage } from '@/lib/copy';

export default function SignupPage() {
  return (
    <>
      <Signup10
        content={{
          title: signupPage.title,
          signUpWithGoogle: signupPage.signUpWithGoogle,
          or: signupPage.or,
          emailPlaceholder: signupPage.emailPlaceholder,
          continue: signupPage.continue,
          termsPrefix: signupPage.termsPrefix,
          termsAnd: signupPage.termsAnd,
          termsLink: signupPage.termsLink,
          privacyLink: signupPage.privacyLink,
          alreadyUser: signupPage.alreadyUser,
          logIn: signupPage.logIn,
          logoAlt: signupPage.logoAlt,
        }}
      />
      <Footer1Section />
    </>
  );
}
