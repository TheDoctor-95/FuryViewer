/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FavouriteArtistDetailComponent } from '../../../../../../main/webapp/app/entities/favourite-artist/favourite-artist-detail.component';
import { FavouriteArtistService } from '../../../../../../main/webapp/app/entities/favourite-artist/favourite-artist.service';
import { FavouriteArtist } from '../../../../../../main/webapp/app/entities/favourite-artist/favourite-artist.model';

describe('Component Tests', () => {

    describe('FavouriteArtist Management Detail Component', () => {
        let comp: FavouriteArtistDetailComponent;
        let fixture: ComponentFixture<FavouriteArtistDetailComponent>;
        let service: FavouriteArtistService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [FavouriteArtistDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FavouriteArtistService,
                    JhiEventManager
                ]
            }).overrideTemplate(FavouriteArtistDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FavouriteArtistDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FavouriteArtistService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FavouriteArtist(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.favouriteArtist).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
