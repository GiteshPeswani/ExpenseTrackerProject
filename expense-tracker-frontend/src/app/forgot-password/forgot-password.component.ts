import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {

form: FormGroup;
  message = '';
  loading = false;
   

  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

    
  

  submit() {
    if (this.form.invalid) return;

    this.loading = true;
    this.message = '';

    this.authService.forgotPassword(this.form.value.email).subscribe({
      next: (res: any) => {
        this.message = 'Reset password link has been sent to your email.';  

      },
      error: (err) => {
        this.message = err.error?.body || 'Email not registered!';
        this.loading = false;
      }
    });
  }

}
