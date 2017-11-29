/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MovieDetailComponent } from '../../../../../../main/webapp/app/entities/movie/movie-detail.component';
import { MovieService } from '../../../../../../main/webapp/app/entities/movie/movie.service';
import { Movie } from '../../../../../../main/webapp/app/entities/movie/movie.model';

describe('Component Tests', () => {

    describe('Movie Management Detail Component', () => {
        let comp: MovieDetailComponent;
        let fixture: ComponentFixture<MovieDetailComponent>;
        let service: MovieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [MovieDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MovieService,
                    JhiEventManager
                ]
            }).overrideTemplate(MovieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MovieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Movie(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.movie).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
