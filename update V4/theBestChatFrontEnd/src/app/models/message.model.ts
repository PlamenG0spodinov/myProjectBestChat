import { UserType } from './user.model';

export type MessageType = {
  id?: number;
  sender: UserType;  
  recipient: UserType; 
  content: string;
  messageContent: string;
  timestamp: string;  
};