/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AchievementsAchievsDetailComponent } from '../../../../../../main/webapp/app/entities/achievements-achievs/achievements-achievs-detail.component';
import { AchievementsAchievsService } from '../../../../../../main/webapp/app/entities/achievements-achievs/achievements-achievs.service';
import { AchievementsAchievs } from '../../../../../../main/webapp/app/entities/achievements-achievs/achievements-achievs.model';

describe('Component Tests', () => {

    describe('AchievementsAchievs Management Detail Component', () => {
        let comp: AchievementsAchievsDetailComponent;
        let fixture: ComponentFixture<AchievementsAchievsDetailComponent>;
        let service: AchievementsAchievsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [AchievementsAchievsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AchievementsAchievsService,
                    JhiEventManager
                ]
            }).overrideTemplate(AchievementsAchievsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AchievementsAchievsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AchievementsAchievsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AchievementsAchievs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.achievementsAchievs).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
