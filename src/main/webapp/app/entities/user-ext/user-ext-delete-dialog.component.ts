import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserExt } from './user-ext.model';
import { UserExtPopupService } from './user-ext-popup.service';
import { UserExtService } from './user-ext.service';

@Component({
    selector: 'jhi-user-ext-delete-dialog',
    templateUrl: './user-ext-delete-dialog.component.html'
})
export class UserExtDeleteDialogComponent {

    userExt: UserExt;

    constructor(
        private userExtService: UserExtService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userExtService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userExtListModification',
                content: 'Deleted an userExt'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-ext-delete-popup',
    template: ''
})
export class UserExtDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userExtPopupService: UserExtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userExtPopupService
                .open(UserExtDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
