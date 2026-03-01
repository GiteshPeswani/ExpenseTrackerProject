import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { observableToBeFn } from 'rxjs/internal/testing/TestScheduler';
import { ForgotPasswordComponent } from '../forgot-password/forgot-password.component';

@Injectable({ providedIn: 'root' })
export class AuthService {

        apiUrl: string = 'http://localhost:8088';


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
    return this.http.post(`${this.apiUrl}/reset-password-final`,body);

}
emailbhejo(emailform:any):Observable<any>{
  console.log(emailform);
  return this.http.post(`${this.apiUrl}/forgot-password`,emailform);


}
registerkaro(registerForm:any):Observable<any>{
  console.log("Output:",registerForm)
  return this.http.post(`${this.apiUrl}/register`,registerForm);


}

}
