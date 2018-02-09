/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MovieBDetailComponent } from '../../../../../../main/webapp/app/entities/movie-b/movie-b-detail.component';
import { MovieBService } from '../../../../../../main/webapp/app/entities/movie-b/movie-b.service';
import { MovieB } from '../../../../../../main/webapp/app/entities/movie-b/movie-b.model';

describe('Component Tests', () => {

    describe('MovieB Management Detail Component', () => {
        let comp: MovieBDetailComponent;
        let fixture: ComponentFixture<MovieBDetailComponent>;
        let service: MovieBService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [MovieBDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MovieBService,
                    JhiEventManager
                ]
            }).overrideTemplate(MovieBDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MovieBDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieBService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MovieB(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.movieB).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
