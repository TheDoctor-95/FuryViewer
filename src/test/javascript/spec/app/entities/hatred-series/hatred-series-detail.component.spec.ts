/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HatredSeriesDetailComponent } from '../../../../../../main/webapp/app/entities/hatred-series/hatred-series-detail.component';
import { HatredSeriesService } from '../../../../../../main/webapp/app/entities/hatred-series/hatred-series.service';
import { HatredSeries } from '../../../../../../main/webapp/app/entities/hatred-series/hatred-series.model';

describe('Component Tests', () => {

    describe('HatredSeries Management Detail Component', () => {
        let comp: HatredSeriesDetailComponent;
        let fixture: ComponentFixture<HatredSeriesDetailComponent>;
        let service: HatredSeriesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [HatredSeriesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HatredSeriesService,
                    JhiEventManager
                ]
            }).overrideTemplate(HatredSeriesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HatredSeriesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HatredSeriesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HatredSeries(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.hatredSeries).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
