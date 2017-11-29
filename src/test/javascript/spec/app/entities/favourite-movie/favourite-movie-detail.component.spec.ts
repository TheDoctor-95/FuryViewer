/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FavouriteMovieDetailComponent } from '../../../../../../main/webapp/app/entities/favourite-movie/favourite-movie-detail.component';
import { FavouriteMovieService } from '../../../../../../main/webapp/app/entities/favourite-movie/favourite-movie.service';
import { FavouriteMovie } from '../../../../../../main/webapp/app/entities/favourite-movie/favourite-movie.model';

describe('Component Tests', () => {

    describe('FavouriteMovie Management Detail Component', () => {
        let comp: FavouriteMovieDetailComponent;
        let fixture: ComponentFixture<FavouriteMovieDetailComponent>;
        let service: FavouriteMovieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [FavouriteMovieDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FavouriteMovieService,
                    JhiEventManager
                ]
            }).overrideTemplate(FavouriteMovieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FavouriteMovieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FavouriteMovieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FavouriteMovie(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.favouriteMovie).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
