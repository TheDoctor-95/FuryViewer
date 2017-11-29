/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HatredMovieDetailComponent } from '../../../../../../main/webapp/app/entities/hatred-movie/hatred-movie-detail.component';
import { HatredMovieService } from '../../../../../../main/webapp/app/entities/hatred-movie/hatred-movie.service';
import { HatredMovie } from '../../../../../../main/webapp/app/entities/hatred-movie/hatred-movie.model';

describe('Component Tests', () => {

    describe('HatredMovie Management Detail Component', () => {
        let comp: HatredMovieDetailComponent;
        let fixture: ComponentFixture<HatredMovieDetailComponent>;
        let service: HatredMovieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [HatredMovieDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HatredMovieService,
                    JhiEventManager
                ]
            }).overrideTemplate(HatredMovieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HatredMovieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HatredMovieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HatredMovie(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.hatredMovie).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
