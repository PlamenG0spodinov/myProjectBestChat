import { UserType } from "./user.model";

export type FriendType = {
    id?: number;
    userId: number;
    friendId: number;
    username: UserType;
   
  };