import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Episode } from './episode.model';
import { EpisodeService } from './episode.service';

@Injectable()
export class EpisodePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private episodeService: EpisodeService

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
                this.episodeService.find(id).subscribe((episode) => {
                    if (episode.releaseDate) {
                        episode.releaseDate = {
                            year: episode.releaseDate.getFullYear(),
                            month: episode.releaseDate.getMonth() + 1,
                            day: episode.releaseDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.episodeModalRef(component, episode);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.episodeModalRef(component, new Episode());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    episodeModalRef(component: Component, episode: Episode): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.episode = episode;
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
