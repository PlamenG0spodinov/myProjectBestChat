export interface ChannelMemberType {
   
    channel: {
      id: string; 
      name: string;
    };

    user: {
      id?: number;
      username: string;
    };
  }