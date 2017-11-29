/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserExtDetailComponent } from '../../../../../../main/webapp/app/entities/user-ext/user-ext-detail.component';
import { UserExtService } from '../../../../../../main/webapp/app/entities/user-ext/user-ext.service';
import { UserExt } from '../../../../../../main/webapp/app/entities/user-ext/user-ext.model';

describe('Component Tests', () => {

    describe('UserExt Management Detail Component', () => {
        let comp: UserExtDetailComponent;
        let fixture: ComponentFixture<UserExtDetailComponent>;
        let service: UserExtService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [UserExtDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserExtService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserExtDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserExtDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserExtService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserExt(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userExt).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
