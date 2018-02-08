/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AchievementDetailComponent } from '../../../../../../main/webapp/app/entities/achievement/achievement-detail.component';
import { AchievementService } from '../../../../../../main/webapp/app/entities/achievement/achievement.service';
import { Achievement } from '../../../../../../main/webapp/app/entities/achievement/achievement.model';

describe('Component Tests', () => {

    describe('Achievement Management Detail Component', () => {
        let comp: AchievementDetailComponent;
        let fixture: ComponentFixture<AchievementDetailComponent>;
        let service: AchievementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [AchievementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AchievementService,
                    JhiEventManager
                ]
            }).overrideTemplate(AchievementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AchievementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AchievementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Achievement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.achievement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
