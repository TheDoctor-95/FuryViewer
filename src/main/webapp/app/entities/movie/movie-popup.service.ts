import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Movie } from './movie.model';
import { MovieService } from './movie.service';

@Injectable()
export class MoviePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private movieService: MovieService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.movieService.find(id).subscribe((movie) => {
                    if (movie.releaseDate) {
                        movie.releaseDate = {
                            year: movie.releaseDate.getFullYear(),
                            month: movie.releaseDate.getMonth() + 1,
                            day: movie.releaseDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.movieModalRef(component, movie);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.movieModalRef(component, new Movie());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    movieModalRef(component: Component, movie: Movie): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.movie = movie;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
