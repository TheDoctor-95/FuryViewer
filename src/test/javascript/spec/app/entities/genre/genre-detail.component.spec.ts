/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GenreDetailComponent } from '../../../../../../main/webapp/app/entities/genre/genre-detail.component';
import { GenreService } from '../../../../../../main/webapp/app/entities/genre/genre.service';
import { Genre } from '../../../../../../main/webapp/app/entities/genre/genre.model';

describe('Component Tests', () => {

    describe('Genre Management Detail Component', () => {
        let comp: GenreDetailComponent;
        let fixture: ComponentFixture<GenreDetailComponent>;
        let service: GenreService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [GenreDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GenreService,
                    JhiEventManager
                ]
            }).overrideTemplate(GenreDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GenreDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenreService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Genre(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.genre).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
