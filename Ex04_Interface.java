
interface Irepairable{
    
}

class Unit2{
    int hitpoint; // 기본에너지
    final int MAX_HP;// 최대에너지
    Unit2(int hp){
        this.MAX_HP = hp;
    }
}

//지상 유닛
class GroundUnit extends Unit2{

    GroundUnit(int hp) {
        super(hp);
        
    }
    
}


//공중 유닛
class AirUnit extends Unit2{

    AirUnit(int hp) {
        super(hp);
        
    }
    
}

//건물
class CommandCenter implements Irepairable{
    
}


class Tank2 extends GroundUnit implements Irepairable{

    Tank2() {
        super(50);
        this.hitpoint = this.MAX_HP;
        
    }
    
    @Override
    public String toString() {
        return"[Tank2]";
    }
}

class Marine2 extends GroundUnit{

    Marine2() {
        super(50);
        this.hitpoint = this.MAX_HP;
    }

    @Override
    public String toString() {
        return "[Marin2]";
    }
    
}

//Scv 자원채취(광물, 치료..에너지공급)
class Scv extends GroundUnit implements Irepairable{

    Scv() {
        super(50);
        this.hitpoint = this.MAX_HP;
    }

    @Override
    public String toString() {
        return "[Scv]";
    }
    
    //Scv 구체화, 특수화된 기능
    //repair 가능함
    /*
    void repair(Tank2 tank) {
        if(tank.hitpoint != tank.MAX_HP) {
            System.out.println("데미지...");
            tank.hitpoint += 5;
        }
    }
    
    void repair(Scv scv) {
        if(scv.hitpoint != scv.MAX_HP) {
            System.out.println("데미지...");
            scv.hitpoint += 5;
        }
    }
    
    Unit 종류가 30개(25개)
    Scv repair 해야하는 Unit개수가 증가하면 함수의 개수도 비례함
    고민: 하나의 함수가 Unit repair 할 수 있다면
    (전자제품)...상속관계 부모타입...다형성...같은거 아니야?
    
    void repair(Unit2 unit){} >> Marine(x), Scv(0), Tank2(0)
    void repair(GroundUnit unit){} >> Marine(x), Scv(0), Tank2(0)
    
    CommandCenter repair 대상 (유닛과 연관성이 없어요)
    
    고민
    Marine2 Scv Tank2 CommandCenter 서로 연관성이 없다....
    
    인터페이스 Irepairable 부모로 만들어
    class Scv extends GroundUnit     implements Irepairable
    class Tank2 extends GroundUnit   implements Irepairable
    class CommandCenter              implements Irepairable
    
    Irepairable는 Scv                  부모타입이다
    Irepairable는 Tank2                부모타입이다
    Irepairable는 CommandCenter        부모타입이다
    */
    void repair(Irepairable repairunit) {
        //repairUnit parameter는 Irepairable 인터페이스를 구현하는 객체의 주
        //void repair(new Tank2())
        //void repair(new CommandCenter())
        //void repair(new Scv())
        //부모타입은 자식타입의 주소를 받을 수 있는데...부모것만 볼 수 있음
        
        //정리
        //1. CommandCenter 와 Tank2, Scv 수리방법이 틀리다
        //2. Irepairable repairunit 은 수리할 자원을 가지고 있지않다
        // 그럼 Irepairable repairunit 통해서 위 3가지 Unit2 온다고 가정하고
        
        // CommandCenter 와 Tank2,Scv 구문점 Unit2이냐 아니냐...
        // 타입비교(연산자: instanceof) 활용
        //https://cafe.naver.com/bit2021/141
        if(repairunit instanceof Unit2) {//Tank2야 나 Unit2타입이야
           Unit2 unit = (Unit2)repairunit; //unit >>Tank2, Marine2...
           //Tank2 에서 부모타입인 Unit2 자원만 접근
           
           //부모타입의 주소를 자식이 가질려면 자식으로 캐스팅 해야 한다
           if(unit.hitpoint != unit.MAX_HP) {
               unit.hitpoint = unit.MAX_HP;
           }
        }else {
            //부모타입이 Unit2타입이 아닌것
            System.out.println("다른 충전 방식을 통해서..Unit2타입이 아니에요");
        }
    }
}



public class Ex04_Interface {
    public static void main(String[] args) {
        Tank2 tank = new Tank2();
        Marine2 marine = new Marine2();
        Scv scv = new Scv();
        
        //전투
        tank.hitpoint -= 20;
        System.out.println("탱크: "+tank.hitpoint);
        System.out.println("Scv 수리요청");
        scv.repair(tank);
        System.out.println("탱크 수리완료: "+tank.hitpoint);
        
        scv.hitpoint -= 10;
        System.out.println("scv: "+tank.hitpoint);
        System.out.println("Scv 수리요청");
        scv.repair(scv);
        System.out.println("scv 수리완료: "+tank.hitpoint);
        
        CommandCenter center = new CommandCenter();
        scv.repair(center);
        
        //scv.repair(marine); 컴파일조차 안되요...
    }
}
