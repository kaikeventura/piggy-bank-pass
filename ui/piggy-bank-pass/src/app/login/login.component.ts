import { Component } from '@angular/core';
import {MatSlideToggle} from "@angular/material/slide-toggle";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatSlideToggle
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

}
