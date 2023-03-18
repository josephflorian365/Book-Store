export interface Credentials {
    email: string;
    password: string;
}

export interface User {
    id: number;
    firstName: string;
    lastName: string;
    fullName: string;
    email: string;
    role: string;
}