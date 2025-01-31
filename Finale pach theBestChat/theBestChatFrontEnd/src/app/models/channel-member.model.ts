export interface ChannelMemberType {
   
    channel: {
      id: number; 
      name: string;
    };

    user: {
      id?: number;
      username: string;
    };
  }