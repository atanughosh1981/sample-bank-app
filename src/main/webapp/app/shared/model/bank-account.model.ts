export interface IBankAccount {
    id?: number;
    username?: string;
    password?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    phone?: string;
    address?: string;
    postalCode?: string;
    panNumber?: string;
}

export class BankAccount implements IBankAccount {
    constructor(
        public id?: number,
        public username?: string,
        public password?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phone?: string,
        public address?: string,
        public postalCode?: string,
        public panNumber?: string
    ) {}
}
