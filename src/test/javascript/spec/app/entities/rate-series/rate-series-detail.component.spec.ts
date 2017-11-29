/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RateSeriesDetailComponent } from '../../../../../../main/webapp/app/entities/rate-series/rate-series-detail.component';
import { RateSeriesService } from '../../../../../../main/webapp/app/entities/rate-series/rate-series.service';
import { RateSeries } from '../../../../../../main/webapp/app/entities/rate-series/rate-series.model';

describe('Component Tests', () => {

    describe('RateSeries Management Detail Component', () => {
        let comp: RateSeriesDetailComponent;
        let fixture: ComponentFixture<RateSeriesDetailComponent>;
        let service: RateSeriesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [RateSeriesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RateSeriesService,
                    JhiEventManager
                ]
            }).overrideTemplate(RateSeriesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RateSeriesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RateSeriesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RateSeries(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rateSeries).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
