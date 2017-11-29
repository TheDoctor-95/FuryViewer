/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SeriesDetailComponent } from '../../../../../../main/webapp/app/entities/series/series-detail.component';
import { SeriesService } from '../../../../../../main/webapp/app/entities/series/series.service';
import { Series } from '../../../../../../main/webapp/app/entities/series/series.model';

describe('Component Tests', () => {

    describe('Series Management Detail Component', () => {
        let comp: SeriesDetailComponent;
        let fixture: ComponentFixture<SeriesDetailComponent>;
        let service: SeriesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [SeriesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SeriesService,
                    JhiEventManager
                ]
            }).overrideTemplate(SeriesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeriesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeriesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Series(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.series).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
