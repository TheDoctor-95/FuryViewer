/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MovieStatsDetailComponent } from '../../../../../../main/webapp/app/entities/movie-stats/movie-stats-detail.component';
import { MovieStatsService } from '../../../../../../main/webapp/app/entities/movie-stats/movie-stats.service';
import { MovieStats } from '../../../../../../main/webapp/app/entities/movie-stats/movie-stats.model';

describe('Component Tests', () => {

    describe('MovieStats Management Detail Component', () => {
        let comp: MovieStatsDetailComponent;
        let fixture: ComponentFixture<MovieStatsDetailComponent>;
        let service: MovieStatsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [MovieStatsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MovieStatsService,
                    JhiEventManager
                ]
            }).overrideTemplate(MovieStatsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MovieStatsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieStatsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MovieStats(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.movieStats).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
