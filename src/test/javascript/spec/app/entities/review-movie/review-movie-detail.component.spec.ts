/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReviewMovieDetailComponent } from '../../../../../../main/webapp/app/entities/review-movie/review-movie-detail.component';
import { ReviewMovieService } from '../../../../../../main/webapp/app/entities/review-movie/review-movie.service';
import { ReviewMovie } from '../../../../../../main/webapp/app/entities/review-movie/review-movie.model';

describe('Component Tests', () => {

    describe('ReviewMovie Management Detail Component', () => {
        let comp: ReviewMovieDetailComponent;
        let fixture: ComponentFixture<ReviewMovieDetailComponent>;
        let service: ReviewMovieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [ReviewMovieDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReviewMovieService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReviewMovieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReviewMovieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReviewMovieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ReviewMovie(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reviewMovie).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
