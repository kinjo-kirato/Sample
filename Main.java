
import java.util.Random;
import java.util.Scanner;

class Character {//キャラクターの属性を管理するクラス
    String name;
    int hp;
    int mp;
    int item1;
    int item2;

    Character(String name, int hp, int mp, int item1, int item2) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.item1 = item1;
        this.item2 = item2;
    }
    class MagicEffectResult {//魔法の管理
        boolean magicActive1;//攻撃上昇魔法の状態
        boolean magicActive2;//防御上昇魔法の状態
    
        MagicEffectResult(boolean magicActive1, boolean magicActive2) {
            this.magicActive1 = magicActive1;
            this.magicActive2 = magicActive2;
        }
    }
    //魔法の効果時間を減少させるメソッド
    MagicEffectResult decreaseMagicEffectDuration(int magicActive1Effect, int magicActive2Effect,boolean magicEffect) {
        boolean magicActive1 = false;
        boolean newMagicActive1 = magicActive1;
        boolean magicActive2 = false;
        boolean newMagicActive2 = magicActive2;
        
        if (magicActive1Effect > 0 && magicEffect == true) {
            magicActive1Effect--;
            int Effect1 = 1;
            if (magicActive1Effect > 0) {
                System.out.print("攻撃上昇魔法の効果時間は残り" + magicActive1Effect + "ターン!");
            } if (magicActive1Effect == 0 && Effect1==1) {
                System.out.println("攻撃上昇魔法の効果が切れました！");
                Effect1 = 0;
                newMagicActive1 = false;

            }
        }
        if (magicActive2Effect > 0 && magicEffect == true) {
            magicActive2Effect--;
            int Effect2 = 1;
            if (magicActive2Effect > 0) {
                System.out.print("防御上昇魔法の効果時間は残り" + magicActive2Effect + "ターン!");
            } if (magicActive2Effect == 0 && Effect2 == 1) {
                System.out.println("防御上昇魔法の効果が切れました！");
                Effect2 = 0;
                newMagicActive2 = false;
            }
        }
        return new MagicEffectResult(newMagicActive1, newMagicActive2);
    }
}

class BattleGame {
    private Random random;
    private Scanner scanner;
    private Character slime;
    private Character brave;
    private boolean magicActive1;
    private boolean magicActive2;

    BattleGame() {
        random = new Random();
        scanner = new Scanner(System.in);
        slime = new Character("スライム", 150, 0, 0, 0);
        brave = new Character("勇者", 200, 50, 5, 5);
        magicActive1 = false;
        magicActive2 = false;
    }

    public void startBattle() throws Exception {
        boolean battleOver = false;

        int magicActive1Effect = 0;
        int magicActive2Effect = 0;

        while (!battleOver) {
            if (slime.hp <= 0 || brave.hp <= 0) {
                battleOver = true;
                break;
            }
            System.out.println("プレイヤーの行動を選択してください:");
            System.out.println("1. 攻撃");
            System.out.println("2. 魔法");
            System.out.println("3. アイテム");
            System.out.println("4. 逃げる");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:// 攻撃処理
                {
                    if (random.nextDouble() <= 0.15) {//勇者の攻撃が外れた場合の処理
                        System.out.println("勇者の攻撃が外れた！");
                         Character.MagicEffectResult result = brave.decreaseMagicEffectDuration(magicActive1Effect, magicActive2Effect, true);
                         magicActive1 = result.magicActive1;//魔法の効果時間を減少させる
                         magicActive2 = result.magicActive2;
                         if(magicActive1 == true){
                            if(magicActive1Effect == 0){
                                magicActive1 = false;
                            }
                        }
                        if(magicActive2 == true){
                            if(magicActive2Effect == 0){
                                magicActive2 = false;
                            }
                        }
                    } else {
                        int attackDamage = random.nextInt(10) + 10;//10~19
                        if(magicActive1Effect > 0){//攻撃上昇魔法の効果:与えるダメージ+5
                            attackDamage += 5;
                            }
              
                        slime.hp -= attackDamage;//スライムにダメージを与える処理
                        if (slime.hp <= 0) {
                            slime.hp = 0;
                        }
                        System.out.println("勇者はスライムに攻撃！" + attackDamage + "のダメージをスライムに与えた！ 残りのスライムのHP" + slime.hp);
                        if(magicActive1Effect > 0){
                            magicActive1Effect -=1;
                            }
                      
                        if(magicActive2Effect > 0){
                            magicActive2Effect -= 1;
                           }
                       
                        if (slime.hp<= 0) {
                            slime.hp = 0;
                            break;
                        }
                    }
                 
                    break;
                }
                case 2: // 魔法処理
                {
                    System.out.println("どの魔法を使用しますか?");
                    System.out.println("1. 攻撃力上昇魔法");
                    System.out.println("2. 防御力上昇魔法");
                    System.out.println("3. 炎の攻撃魔法");
                    System.out.println("4. 戻る");

                    int magicChoice = scanner.nextInt();

                    if (magicChoice == 4) {
                        continue; // 戻る選択が選ばれた場合、ループを抜けて前の選択に戻る
                    }

                    if (magicChoice == 1) {
                        if (magicActive1 == true ) {
                            System.out.println("攻撃上昇魔法は既に使用中です！");
                            continue; // 既に魔法が使用中の場合、選択をやり直す
                        } if (magicActive1 == false && brave.mp >= 8){//魔法が使用できる場合の処理
                           magicActive1 = true;
                           magicActive1Effect = 5;
                           brave.mp -= 8;
                           System.out.println("勇者は攻撃力上昇の魔法を唱えた！");
                           System.out.println("残りMPは"+brave.mp+"です");
                        }
                        if (brave.mp < 8) {
                            System.out.println("MPが足りないため、魔法を使用できません！");
                            continue; // MPが足りない場合、選択をやり直す
                        }
                    } else if (magicChoice == 2) {
                        if (magicActive2 == true) {
                            System.out.println("防御上昇魔法は既に使用中です！");
                            continue; // 既に魔法が使用中の場合、選択をやり直す
                        }
                        if (magicActive2 == false && brave.mp >= 8) {//魔法が使用できる場合の処理
                             magicActive2 = true;
                             magicActive2Effect = 5;
                             brave.mp -= 8;
                             System.out.println("勇者は防御力上昇の魔法を唱えた！");
                             System.out.println("残りMPは"+brave.mp+"です");
                             if (brave.mp < 8) {
                                break;
                            }
                        }
                        if (brave.mp < 8) {
                            System.out.println("MPが足りないため、魔法を使用できません！");
                            continue; // MPが足りない場合、選択をやり直す
                        }
                    } else if (magicChoice == 3) {
                        if (brave.mp < 8) {
                            System.out.println("MPが足りないため、魔法を使用できません！");
                            continue; // MPが足りない場合、選択をやり直す
                        }
                        int magicDamage = random.nextInt(30) + 10;
                        slime.hp -= magicDamage;
                        if (slime.hp <= 0) {
                            slime.hp= 0;
                        }
                        System.out.println("勇者は炎の攻撃魔法を使った！" + magicDamage + "のダメージをスライムに与えた！ 残りのスライムのHP" + slime.hp);
                        if (slime.hp == 0) {
                            battleOver = true;
                            break;
                        }
                    } else {
                        System.out.println("無効な魔法選択です！");
                    }
                  
                  break;
                }
                case 3:  // アイテム処理
                {
                    System.out.println("どのアイテムを使用しますか?");
                    System.out.println("1. 薬草 (残り:"+ brave.item1 +")");
                    System.out.println("2. すごい薬草 (残り:"+ brave.item2 +")");
                    System.out.println("3. 戻る");

                    int itemChoice = scanner.nextInt();

                    if (itemChoice == 3) {
                        continue; // 戻る選択が選ばれた場合、ループを抜けて前の選択に戻る
                    }

                    if (itemChoice == 1 && brave.hp < 200 && brave.item1 > 0) {
                        brave.hp += 30;
                        brave.item1 -= 1;
                        System.out.println("勇者は薬草を使った！ 30の回復をした！ 残りの勇者のHP" + brave.hp);
                    } else if (itemChoice == 2 && brave.hp < 200 && brave.item2 > 0) {
                        brave.hp += 60;
                        brave.item2 -= 1;
                        System.out.println("勇者はすごい薬草を使った！ 60の回復をした！ 残りの勇者のHP" + brave.hp);
                    } else if (itemChoice == 1 || itemChoice == 2) {
                        System.out.println("既にHPが満タンです！");
                    } else {
                        System.out.println("無効なアイテム選択です！");
                    }
                }
            break;
                case 4:// 逃げる処理
                if (random.nextDouble() <= 0.3) {//「逃げる」が成功した場合の処理
                    System.out.println("勇者は逃げた！");
                    battleOver = true;
                } else {//「逃げる」が失敗した場合の処理
                    System.out.println("勇者は逃げようとしたが失敗した！");
                    int slimeDamage = random.nextInt(10) + 10;
                    brave.hp -= slimeDamage;
                    if (brave.hp <= 0) {
                        brave.hp = 0;
                        break;
                    }
                }
                break;
                  default://無効な選択な場合の処理
                    System.out.println("無効な選択です。");
                   continue;
            }
            if (!battleOver) {
                // スライムのターン
                if (slime.hp > 0 && brave.hp > 0 ) {
                    int slimeDamage;
                    if (random.nextDouble() <= 0.15) {
                        System.out.println("スライムの攻撃が外れた！");
                        slimeDamage = 0;
                    } else if (random.nextDouble() <= 0.1) {
                        slimeDamage = random.nextInt(6) + 25; // 25〜30のダメージ
                        System.out.println("スライムの攻撃が会心の一撃を出した！");
                    } else {
                        slimeDamage = random.nextInt(10) + 10; // 10〜19のダメージ
                    }
         
                if(magicActive2Effect > 0){//防御上昇魔法使用中受けるダメージ−５
                     slimeDamage -= 5;
                             
                    }
                    brave.hp -= slimeDamage;

                    if (slimeDamage > 0) {
                        if (brave.hp < 0) {//HPがマイナスになった場合HPを０にする
                            brave.hp = 0;
                        }
                        System.out.println("スライムは勇者に攻撃！" + slimeDamage + "のダメージを勇者に与えた！ 残りの勇者のHP" + brave.hp);
                        if (brave.hp == 0) {
                            battleOver = true;
                            break;
                        }
                    }
                }
            }
            brave.decreaseMagicEffectDuration(magicActive1Effect, magicActive2Effect,true);

             if(magicActive1Effect == 0){
                magicActive1 = false;
            }
            if(magicActive1 == true){
                if(magicActive1Effect == 0){
                    magicActive1 = false;
                }
            }
  
            if(magicActive2Effect == 0){
                magicActive2 = false;
            }
            if(magicActive2 == true){
                if(magicActive2Effect == 0){
                    magicActive2 = false;
                }
            }

        }
        if (slime.hp <= 0) {
            System.out.println("勇者は勝利した！");
        }
        if (brave.hp <= 0) {
            System.out.println("勇者は" + slime.name + "に負けた");
        }
        System.out.println("戦闘が終了した");

        scanner.close();
    }

    public void closeScanner() {
    }
}
public class Main {//メインの処理
    public static void main(String[] args) throws Exception {
        BattleGame game = new BattleGame();
        game.startBattle();
        game.closeScanner();
    }
}