import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Social } from './social.model';
import { SocialPopupService } from './social-popup.service';
import { SocialService } from './social.service';

@Component({
    selector: 'jhi-social-delete-dialog',
    templateUrl: './social-delete-dialog.component.html'
})
export class SocialDeleteDialogComponent {

    social: Social;

    constructor(
        private socialService: SocialService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.socialService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'socialListModification',
                content: 'Deleted an social'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-social-delete-popup',
    template: ''
})
export class SocialDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private socialPopupService: SocialPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.socialPopupService
                .open(SocialDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
