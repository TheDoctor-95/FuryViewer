/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReviewSeriesDetailComponent } from '../../../../../../main/webapp/app/entities/review-series/review-series-detail.component';
import { ReviewSeriesService } from '../../../../../../main/webapp/app/entities/review-series/review-series.service';
import { ReviewSeries } from '../../../../../../main/webapp/app/entities/review-series/review-series.model';

describe('Component Tests', () => {

    describe('ReviewSeries Management Detail Component', () => {
        let comp: ReviewSeriesDetailComponent;
        let fixture: ComponentFixture<ReviewSeriesDetailComponent>;
        let service: ReviewSeriesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [ReviewSeriesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReviewSeriesService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReviewSeriesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReviewSeriesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReviewSeriesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ReviewSeries(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reviewSeries).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
