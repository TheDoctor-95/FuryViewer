/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ArtistDetailComponent } from '../../../../../../main/webapp/app/entities/artist/artist-detail.component';
import { ArtistService } from '../../../../../../main/webapp/app/entities/artist/artist.service';
import { Artist } from '../../../../../../main/webapp/app/entities/artist/artist.model';

describe('Component Tests', () => {

    describe('Artist Management Detail Component', () => {
        let comp: ArtistDetailComponent;
        let fixture: ComponentFixture<ArtistDetailComponent>;
        let service: ArtistService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [ArtistDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ArtistService,
                    JhiEventManager
                ]
            }).overrideTemplate(ArtistDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArtistDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArtistService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Artist(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.artist).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
