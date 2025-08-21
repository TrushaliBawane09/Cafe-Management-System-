import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AlertServiceService {

  constructor() { }

  showAlert(
    icon: 'success' | 'error' | 'warning' | 'info' | 'question',
    text: any,
    confirmButtonText = 'OK'
  ) {
    return Swal.fire({
      icon,
      text,
      confirmButtonText,
    });
  }

  showDeleteConfirm(message: string = "Are you sure you want to delete this?") {
    return Swal.fire({
      title: 'Confirm Delete',
      text: message,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Yes, Delete',
      cancelButtonText: 'No, Cancel'
    });
  }
}
