/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SocialDetailComponent } from '../../../../../../main/webapp/app/entities/social/social-detail.component';
import { SocialService } from '../../../../../../main/webapp/app/entities/social/social.service';
import { Social } from '../../../../../../main/webapp/app/entities/social/social.model';

describe('Component Tests', () => {

    describe('Social Management Detail Component', () => {
        let comp: SocialDetailComponent;
        let fixture: ComponentFixture<SocialDetailComponent>;
        let service: SocialService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [SocialDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SocialService,
                    JhiEventManager
                ]
            }).overrideTemplate(SocialDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SocialDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SocialService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Social(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.social).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
