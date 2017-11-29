/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SeriesStatsDetailComponent } from '../../../../../../main/webapp/app/entities/series-stats/series-stats-detail.component';
import { SeriesStatsService } from '../../../../../../main/webapp/app/entities/series-stats/series-stats.service';
import { SeriesStats } from '../../../../../../main/webapp/app/entities/series-stats/series-stats.model';

describe('Component Tests', () => {

    describe('SeriesStats Management Detail Component', () => {
        let comp: SeriesStatsDetailComponent;
        let fixture: ComponentFixture<SeriesStatsDetailComponent>;
        let service: SeriesStatsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [SeriesStatsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SeriesStatsService,
                    JhiEventManager
                ]
            }).overrideTemplate(SeriesStatsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeriesStatsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeriesStatsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SeriesStats(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.seriesStats).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
