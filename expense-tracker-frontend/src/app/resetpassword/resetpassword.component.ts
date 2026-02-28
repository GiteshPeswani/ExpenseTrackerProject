import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetPasswordComponent implements OnInit {

  form!: FormGroup;
  token: string = '';
  message = '';
  isTokenValid = false;
  loading = false;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.token = this.route.snapshot.paramMap.get('token') || '';


    this.form = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required]
    });

  
    if (!this.token) {
      alert('Invalid reset link!');
      this.router.navigate(['/login']);
      return;
    }

    // ✅ Token validate from backend
    this.authService.validateresettoken(this.token).subscribe({
      next: () => {
        this.isTokenValid = true;
      },
      error: (err) => {
        alert(err.error?.body || 'Reset link expired or invalid!');
        this.router.navigate(['/login']);
      }
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const { password, confirmPassword } = this.form.value;

    // ❌ Password mismatch
    if (password !== confirmPassword) {
      this.message = 'Passwords do not match!';
      return;
    }

    this.loading = true;

    // ✅ Final password reset call
    this.authService.resetPassword(this.token, password).subscribe({
      next: () => {
        this.message = 'Password reset successful! Redirecting to login...';
        this.loading = false;

        setTimeout(() => {
          this.router.navigate(['/login']);
        } , 3000);
      },
      error: (err) => {
        this.loading = false;
        this.message = err.error?.body || 'Something went wrong!';
      }
    });
  }
}
