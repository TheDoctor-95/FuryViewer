/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SeriesBDetailComponent } from '../../../../../../main/webapp/app/entities/series-b/series-b-detail.component';
import { SeriesBService } from '../../../../../../main/webapp/app/entities/series-b/series-b.service';
import { SeriesB } from '../../../../../../main/webapp/app/entities/series-b/series-b.model';

describe('Component Tests', () => {

    describe('SeriesB Management Detail Component', () => {
        let comp: SeriesBDetailComponent;
        let fixture: ComponentFixture<SeriesBDetailComponent>;
        let service: SeriesBService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [SeriesBDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SeriesBService,
                    JhiEventManager
                ]
            }).overrideTemplate(SeriesBDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeriesBDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeriesBService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SeriesB(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.seriesB).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
