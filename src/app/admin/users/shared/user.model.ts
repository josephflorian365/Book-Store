export interface UserPage {
    content:          User[];
    pageable:         Pageable;
    last:             boolean;
    totalElements:    number;
    totalPages:       number;
    size:             number;
    number:           number;
    sort:             Sort;
    first:            boolean;
    numberOfElements: number;
    empty:            boolean;
}

export interface User {
    id:        number | null;
    firstName: string;
    lastName:  string;
    fullName:  string;
    email:     string;
    password:  string;
    role:      string;
    createdAt: Date;
    updatedAt: Date | null;
}

export interface Pageable {
    sort:       Sort;
    offset:     number;
    pageSize:   number;
    pageNumber: number;
    paged:      boolean;
    unpaged:    boolean;
}

export interface Sort {
    empty:    boolean;
    sorted:   boolean;
    unsorted: boolean;
}
