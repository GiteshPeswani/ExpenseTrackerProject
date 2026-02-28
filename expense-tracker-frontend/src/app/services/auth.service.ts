import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {

  

  constructor(private http: HttpClient) {}

  // 🔐 Validate reset token
  // Token validate karne ke liye
validateresettoken(token: string) {
  return this.http.post('http://localhost:8088/tokenvalidation', { token });
}

// Password update karne ke liye
resetPassword(token: string, password: string) {
  // Backend Map<String, String> body expect kar raha hai
  const body = { token: token, password: password };
  return this.http.post('http://localhost:8088/reset-password-final', body);
}
forgotPassword(email: string) {
  return this.http.post(
    'http://localhost:8088/forgot-password',
    { email:email}
  );
}

}
