import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private fb: FormBuilder,private authService:AuthService){}
    registerForm!: FormGroup;
    showPassword = false;
  isLoading = false;
  shakeForm = false;
  serverError = '';
   features = [
    'Visual spending breakdowns',
    'Smart budget alerts',
    'Multi-currency support',
    'Recurring expense tracking',
    'Monthly reports & insights',
  ];

  
// // Register ke liye data object
//   registerObj: any = {
//     fullName: '',
//     email: '',
//     password: '',
//     confirmPassword: ''
//   };

//   // Backend API URL (Apne register route ke hisaab se change karein)
//   apiUrl: string = 'http://localhost:8088/register';

//   constructor(private http: HttpClient, private router: Router) {}

// onRegister() {
//     console.log("Button Clicked!");
    
//     // 1. Basic Validation
//     if (!this.registerObj.fullName || !this.registerObj.email || !this.registerObj.password) {
//       alert('Bhai, saari fields bharna zaroori hai!');
//       return;
//     }

//     // 2. Password Match Check
//     if (this.registerObj.password !== this.registerObj.confirmPassword) {
//       alert('Password aur Confirm Password match nahi ho rahe!');
//       return;
//     }

//     // 3. Mapping data to match Java User Model
//     // Java Model mein 'name' hai, frontend mein 'fullName'
//     const dataToSend = {
//       name: this.registerObj.fullName, // Frontend fullName -> Backend name
//       email: this.registerObj.email,
//       password: this.registerObj.password,
//       username: this.registerObj.email, // Agar username email hi rakhna hai
//       activeYn: 1 // Default active
//     };

//     console.log("Sending fixed data to backend:", dataToSend);

//     // Yahan 'this.registerObj' ki jagah 'dataToSend' bhein
//     this.http.post(this.apiUrl, dataToSend).subscribe({
//       next: (res: any) => {
//         console.log('Registration Success:', res);
//         alert('Account ban gaya! Ab login kijiye.');
//         this.router.navigateByUrl('/login');
//       },
//       error: (err) => {
//         console.error('Registration Error:', err);
//         // Backend 'body' key mein message bhej raha hai
//         alert(err.error?.body || 'Registration fail ho gaya.');
//       }
//     });
// }
ngOnInit(): void {
    this.registerForm = this.fb.group(
      {
        name: ['', Validators.required],
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(255)]],
         phone:    ['', [Validators.maxLength(20), Validators.pattern(/^[+\d\s\-().]*$/)]],

      },
    );
  }
   isInvalid(field: string): boolean {
    const control = this.registerForm.get(field);
    return !!(control && control.invalid && (control.dirty || control.touched));
  }
  getEmailError(): string {
    const c = this.registerForm.get('email');
    if (c?.errors?.['required']) return 'Email is required.';
    if (c?.errors?.['email']) return 'Please enter a valid email address.';
    return '';
  }
  registerkaro(){
    this.authService.registerkaro(this.registerForm.value).subscribe({
      next(res){
        console.log(res);
      },
      error(err){
        console.log(err);
      }
    })
  }
}

