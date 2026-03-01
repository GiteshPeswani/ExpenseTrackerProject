import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {

emailform: FormGroup;
  message = '';
    isLoading = false;
  shakeForm = false;
  serverError = '';
   emailSent = false;
  sentToEmail = '';



  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) {
    this.emailform = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

    
  

  emailbhejo() {
    if (this.emailform.invalid) return;

    this.isLoading= true;
    this.message = '';

    this.authService.emailbhejo(this.emailform.value).subscribe({
      next: (res: any) => {
        this.message = 'Reset password link has been sent to your email.';  
        this.isLoading=false;

      },
      error: (err) => {
        this.message = err.error?.body || 'Email not registered!';
        this.isLoading = false;
      }
    });
  }
  

}
