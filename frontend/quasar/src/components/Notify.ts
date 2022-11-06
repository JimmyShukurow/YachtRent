import { Notify } from 'quasar';

export function showNotif(message: string, type: string) {
  Notify.create({
    message: message,
    type: type,
  });
}
