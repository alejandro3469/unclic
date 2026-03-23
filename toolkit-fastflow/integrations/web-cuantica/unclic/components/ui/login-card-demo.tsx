'use client';

/**
 * Vista demo login — solo componentes shadcn/ui (Card, Input, Label, Button).
 * Sin animaciones de terceros.
 */
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Button } from '@/components/ui/button';
import { loginCard } from '@/lib/copy';

export function LoginCardDemo() {
  return (
    <Card className="w-full max-w-[350px]">
      <CardHeader>
        <CardTitle>{loginCard.title}</CardTitle>
        <CardDescription>{loginCard.description}</CardDescription>
      </CardHeader>
      <CardContent>
        <form>
          <div className="grid w-full items-center gap-4">
            <div className="flex flex-col space-y-1.5">
              <Label htmlFor="email">{loginCard.emailLabel}</Label>
              <Input
                id="email"
                type="email"
                placeholder={loginCard.emailPlaceholder}
              />
            </div>
            <div className="flex flex-col space-y-1.5">
              <Label htmlFor="password">{loginCard.passwordLabel}</Label>
              <Input
                id="password"
                type="password"
                placeholder={loginCard.passwordPlaceholder}
              />
            </div>
          </div>
        </form>
      </CardContent>
      <CardFooter className="flex justify-between">
        <Button variant="outline">{loginCard.register}</Button>
        <Button>{loginCard.submit}</Button>
      </CardFooter>
    </Card>
  );
}
