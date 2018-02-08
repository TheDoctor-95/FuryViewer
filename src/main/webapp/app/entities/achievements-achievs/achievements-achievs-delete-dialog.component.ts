import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AchievementsAchievs } from './achievements-achievs.model';
import { AchievementsAchievsPopupService } from './achievements-achievs-popup.service';
import { AchievementsAchievsService } from './achievements-achievs.service';

@Component({
    selector: 'jhi-achievements-achievs-delete-dialog',
    templateUrl: './achievements-achievs-delete-dialog.component.html'
})
export class AchievementsAchievsDeleteDialogComponent {

    achievementsAchievs: AchievementsAchievs;

    constructor(
        private achievementsAchievsService: AchievementsAchievsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.achievementsAchievsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'achievementsAchievsListModification',
                content: 'Deleted an achievementsAchievs'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-achievements-achievs-delete-popup',
    template: ''
})
export class AchievementsAchievsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private achievementsAchievsPopupService: AchievementsAchievsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.achievementsAchievsPopupService
                .open(AchievementsAchievsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
