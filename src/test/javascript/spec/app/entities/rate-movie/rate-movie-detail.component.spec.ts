/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RateMovieDetailComponent } from '../../../../../../main/webapp/app/entities/rate-movie/rate-movie-detail.component';
import { RateMovieService } from '../../../../../../main/webapp/app/entities/rate-movie/rate-movie.service';
import { RateMovie } from '../../../../../../main/webapp/app/entities/rate-movie/rate-movie.model';

describe('Component Tests', () => {

    describe('RateMovie Management Detail Component', () => {
        let comp: RateMovieDetailComponent;
        let fixture: ComponentFixture<RateMovieDetailComponent>;
        let service: RateMovieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [RateMovieDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RateMovieService,
                    JhiEventManager
                ]
            }).overrideTemplate(RateMovieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RateMovieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RateMovieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RateMovie(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rateMovie).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
