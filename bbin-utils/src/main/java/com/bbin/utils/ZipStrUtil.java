package com.bbin.utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 *Module:          ZipUtil.java
 *Description:    对字符串的压缩及解压
 *Company:
 *Author:           pantp
 *Date:             May 6, 2012
 */
public class ZipStrUtil {

    public static void main(String[] args) throws IOException {
        // 字符串超过一定的长度
        String str = "ABCdef123中文~!@#$%^&*()_+{};/1111111111111111111111111AAAAAAAAAAAJDLFJDLFJDLFJLDFFFFJEIIIIIIIIIIFJJJJJJJJJJJJALLLLLLLLLLLLLLLLLLLLLL" +
                "LLppppppppppppppppppppppppppppppppppppppppp===========================------------------------------iiiiiiiiiiiiiiiiiiiiiii";
        System.out.println("\n原始的字符串为------->" + str);
        float len0 = str.length();
        System.out.println("原始的字符串长度为------->" + len0);

        String ys = compress(str);
        System.out.println("\n压缩后的字符串为----->" + ys);
        float len1 = ys.length();
        System.out.println("压缩后的字符串长度为----->" + len1);

        String compressStr = "\u001F\u008B\b\\0\\0\\0\\0\\0\\0\\0Õ=ksÜD¶\u009FÇUü\u0007YPÖLìyÙ$Ä\u008F1E\u001Eìr\u008B\u0090T\bEÝÊæºd\u008DìQ¢\u0091f%M\u001Coìª\u0084G\u0012²$\u0081\u0005B\u0092\\r\u001BÂ\\Z\b,àP@pl\u0007þËîhÆþ\u0094¿pO?$uK\u009A\u0019\u008DmX\b&\u0096ºO\u009F>¯î>§û¨Ó7Ñ\u007Fàðþcÿ{ä Pqªºpä\u0095}/¾°_\u0010³ùü«#ûóù\u0003Ç\u000E\b\u007F<vèEáé\\\\¡(\u001C³dÃÖ\u001CÍ4d=\u009F?ø\u0092(\u0088\u0015Ç©\u008Dåósss¹¹\u0091\u009CiÍæ\u008F\u001DÍ#TOçuÓ´Õ\\\\Ù)\u008B\u0093}\u0013¨\býRå2üªª\u008E, ¦YõÏuítIÜo\\Z\u008Ej8Ùcó5U\u0014\u0014òV\u0012\u001Dõ\u008C\u0083±\u008D\u000BJE¶lÕ)Õ\u009D\u0099ì^QÈ\u0003\u0016Gstur\\\"O~÷õ=Áý7a+\u0096Vs\u0004\u0007PRL\\'åÓ2)\u0015\\'\u009Fè\u0013R§eKP\u009C3%Q\u001C÷^\u000F\u001D<´ïàÑ©\u0003/\u001C-\u0089ælU\u00ADN«VP{ø\u000FS\u009D\u0001lGv4eªnéBI\u0010óù\u009A¥*\u009AY·\u0011ÃzN1«9ÅÈ\u009F.\u008E¢\u0016\u0013yBËd7Z\u0005ÛRJb\u001EZWM#\u007FÒÎ\u009Füs]µæ³ÅÜÞÜpî¤-Nn\u001DÕ¼¾M\u0004\u0094\u0096º¶M<²a\u0098u£\\njß&\\\"ÓÐ5CÝ\u008Fß\u0011*aÛÌåfL«ZÜ&Y3ò\u009F_P\bE\u001C\u0016 ö\u0094`©zI´\u009Dy]µ+ª\\n\u00AD\u0019¤\u008A\\r<T,u&@\u0007EH^9\\\\\u0095ï\u0019ËI»\u0096\u0097gUC\u0099G\u0084ÙfÝ©dmÇ\u0082&\u00816Á¸FsÅ\u009CR·\u001D0ÚªF:Û\u000EÁUÕ¶¡×cZmÛ¨<Y\u0006üûòÌïÚ\u0005cq\u0097ÐZû¾yyÉ}ó+÷ÝË¸@-k\u008E0=/\brY\u009EA%ù\\'úÐ\u00885êU\u0018ª\u0085q\u0081¼:ZUÅïOô=\u0095\u009E©\u001B\\n\u009AíÒ\u0019áì\u0013}©§ròIùL\\Z=¦`\u0084\u008FÁÔ1(bI:ù?èæ´¬?\u0087\u008DXQ_V\u00ADÓ:ð2\u0084@\u0015Y©¨c3²n«ø½,;2\u009AçÆ¤\u0093¶iH¸ÌÁïG\u000E¿|LòaÆpG¤J\u001C!¸RfmLÄ::$k\u0086\u0003ÿ\u001F\u0090\u001DUDU\u008B\u0018À®+\\n\bzÌ\\'\u001DaÊ\u0010LÚ\f~\u001B\u0018@\u007Fçª\f\u0082\f®OQæ#Õã\u0018?üµ\u0098A\u008F\u008CÈP\u0013Í\u0098õ\u0088IãzøA¢¬\u0081d^Á\u0013á\u009Cf\u0094Í¹\u009Cn*2¢)\u00874\u008A\\0óùÆÊÚæÝ\u001FàQ\u009B\u0011Ò~\u00034u\u008AÂÂ\u0082À\u0096°\u00059@¨\u009E9<\u0093\u0016óø\tFUMÌL\u0016âAÈ$M\u007Fyª\u0001h¢Õ\u0014¬*Ç\u0080q\u0018\u0006a\u0085§RHáX©hb\\Z&ì\u0081¤\u008B\u0085Ba×nò\u0096¸9ßºXÄ¯\u008B\u0082\\nvÑ#!qt\u0080z°v¼\u0096\u0082ìA\u0083\u0006å´¥ÚuÝÁ\u0086ðTºl*¸&7m\u0096ç3¹`ê%¶\u008D,N\u0018\u0013\u008AØ æ´²S\u0081·áB\u0001¿WTm¶â@Á^òî\u00985a\u008C*\u0018F¡ª\\Z9\u0098\u00055ý\u008F\u0018.;22\u008CÁtu\u00065âzÎ)º\u0006Ï¯¢\u001E²Ã\u0085Ý\u0004\u001FZÌ\u0001\u0092\u0010LiSTÔÈ\u0090«j\\0³_·\u0001L\u0092\\r\u0013¿\u0092q\u0083<\u0007°Ã n?) µÓ\u000ES³Ï1\u008260Ùðm \u0080©5\u009C\u0080\\\"Zà\u008D\u0086EVä¼\u0092°êÐH î@÷é\u0002kõ©\\\\Í´\u009D4´\u0019:K¦\u0085\u0011iqÈ7\bªHÁ\u001BÒB\u009A§KèÇce` Do\u007F©\u000E\u0003b\u0006\u0096Æ²×\u0018,AzÒ7\u0093)P¢\u0094ÉYjÕ<\u00AD\u0082\u0085åóîý\u000Bî·¯µ>_Û¼¹Ô¼ú\u0016i\u0013kV\u0098p2C\f\tdZËàé¡\u0083|\u0086wN@Ã¿\u0090\u0080º\u008E\u0014\u007F¬\u0090±Ðu4ì.x\u0080]ÇÃÈèn\\nÚÎ\u0002iõ,HÉ¤Æë=KD\u001FIõ\u0012\u009EÄÏâ\t9\u008D\u0096\u0003\u0098%1§0ç\u000F\u000Ebt´b¢´§\\0u\u0002\u0098É£÷ÈjÛüðÁæ\u0087ß»÷¯5V¯\u0016\u000Bî¥\u000B\u009B\u007F»\u0003e\u001B?ÿÝ}óS÷ÒRóú×Þ:\u0004èJ\u0080\\0·o._#°îúC÷âjcå\\\\ó«»üú\u009D\u008A\u0099\u0001QÏ¸A\u0014\u0014a/\u0004¼§\u0016Ñ\u001C{\u0096ë{\u0004\u0011\u008Fûn¬¾ÿKwOæ¬ª\u009AÍÒ\u0099\u009A\u009Dêyáã)=<¯\bfMµ`\u001D>b\u0099Õ\u009AórÅ\u009CKWíÙ\u008C7z*ZY=\\0Q\u0014\\ZÎSôEÂºGµº)\u0097¡\u0003RK_\u0082ZdwP%¢ß\\\".\u0005\tõS$Ç\u000B\\'\u0088ö±uÊµ\u009Aj\u0094ÓÒDY;-hå\u0092èõ\u0085\u001CZ(\u009B\u0094¼\u0085\bã ]uÃAÁ\\0\u0087V\u009D¥^3ã jUx°ó\bJµ\u0094Ü¬6#æ\\'\\'ì\u009Al\bØg,\u008930\\Z²sx|\u008D\u0001;zy\\\\\u009C\u0094\u0006A>\u0083\u0012øÙ\\0\u0018¡\u008E\u0097SÎFòÄ\u0095¼\u0088\u0082\u008Aöºø#`IÓU\u0095CZÁ\u00151Hý\\n\u000Eé±©CÄ=æ\u0094ëë%\u0007q$\u0016\u009C\u0088\u0005§è²m\u0097$\\0\u009B6ÏLéò¼jMÍYrM¢\u0092\u0001ÁJU³¬N9ZÍ\u009E:=,QYI\u007FÉb¯\bÖv°²Â¸\u0014\u0087ÉCB«f\u001D}JSÌ)EWeTGE\u008A\u0094%ëNI\u0092°Î$Ï=gô\u0005ü\\\"}I\u0093\\\"Ö\u0086\u0018A\u000B\f\u0005\bYU\u0089Tpb.Â¢ØN-¾\u0004Y\u0095Ä#`UÀ\u0086b\u009EM\u0092\b²¬Éº9\u000Bá\bò.J¢{û^kõç\u008DåÏ\u009A¯¿é\u0019ü\u0013}]¢@\u0018èñ\u0011%rR\u00AD©Pð\u009E0 êKEã8\u0014\u00159\u0015ð\u0092¼\u0098h\u009B¸p\u0093\u00ADâb\\\"5Ä\\'\t÷z\u0096Ô¡ùÑgPðqDSN\u0081÷þjÙ\u007F\u000EK\u00ADóF\u000F\u000EëÀ¥±ÀÃÀAØ8\u0017Ù\u009DEaHóÜZóêçðã^zðïs7\\Z««î\u008F\u009Fn~ò\u0091»úÙÆ£¯\u009AW>ù÷¹\u009BÍ\u001BËî;\u009F5oüÔZZí\u0013\u0004\u0001¡\u0085µ þ¢f;\u0080Z=-ëiéøYQ³\u000F\u0098sÆ\u008BêiU\u0017ÇÄ¢8$Öd\u000BÖ\u008C\u0017Êð\u009AEï2\u0004Ü¦¥9ó\u0010¦@\u0019qxðßÏb\u001E,Õ©[\u00065g\u0080·MË\u0001¸\u0002<ê\u0014m\u0081E\u00831ï\u001E\u0086\\\"Í>\u0004\u0014\u0085ë_\u0002¿\u0019Ê6¾ÿÌ½öcë\u009B»\u00ADw.`Ø\u0083\u0086<\u00AD«eL%ø\u0007[&}?Þ$Ðþâ¹i\u0084\u000BXâ\u0090h\u0002úww¤\u007FÏH7ú[Wïo^|·õÝZkíÎ\u008EÒ\u007FT\u00AD\u0001\u0085~ÌîÑ»·3½{»ÑÛ¼üéÆÝ{Í;\u009Fn,\u007F²£ô>ÇÄD\u0011ªG;R=òL7ª\u0089#\u0093ßøf¹y~y\u000Bdc¹Ä\u0092}\bÇßDØÔD`&\u007FYÕUÅ9ÂYz\u0091á¡\u0018á¡XìÊDcý\u0096ûî\\r¢\u0080-ð\u0080ÇÒ\u0016GigÚwwµr÷ÎjóöW\u001B?¼Ñ¼~s\u000B¤c\u0005÷n5\u009D©\u001EéjëÄj\\Z?ßÝ¢Ñ\u008C´\u009D[ÌêQU1\u00AD27·Ð\u001D0ã\\0\u0018\u0093\u00AD1|ìéÈÇ3£]\\r\u0007Ïü°Ð7V.»_ßh^{g\u000BÜànBÜä·Ä\u000E«\u0096áè@\u0018Ù\u009D\u008C\u001Fà\u0004\\\"°-pR,´UÌ¾}1\u008C8¦#ë\u0087LC\u009DOÊÃð3]×-÷Ú\u0097\u001BËkÍ\u008F^ß¼\u0019ÖFA\\\\<\u0001~½·\u001Ckö1K\u000E\u0096y¿t\u009Fì(\u0095 \u001C\u0085\\'ÞÊ\u009DÓUcÖ© 0\u0017b\u0094\u0019ÓJã&\u0010¿i\u0013!\u0098qmp\u0010C±í\u008Fk\\'r\u001CÁx¯2ê@\u0088\u0019\u009F:Çª#\\\"\u0012 i¾õ\u0010bQwé\\r´bg\u0018F<\u0014\u008B}è\u0007ð`Ü\u0019\u0001<Þ\\'\u001DKÞo\\ZàïØàß\u0004î2\u0005Ã\u0018\bà4zôL\u008E\u0081Ká\u001FìÞÈ\u008A\u0002s\u0006roP\u0083ªZ}\u008E\u0014\\08rw\u00104q\u0083ªZ9\\0\u0082Y\u001Et\u001B\u0080DÚNëu\u008BwÁ\u0004òç©´SÑlÒ\u0092}Îä\u001CóEsNµöË6¸î\u0099ñ\u0014Þ}#\u009A¤Dö\u0097Dq`\\0\u0093R\u0082G¬)¨F\u009E\u001FÑ\u009A¬«\u0096\u0093\u0016\u009B_ÿ\u0013|y\u0018Ù\u008DÕ¥ÆÊ×\u008F×ßÞXþ±uïJëë·þsî5ò#b9¤È\u0014O\u0005íµßXþ´õþ½æ¥\u001F\u001B+WÐÀº}ïñúyÒ n\u009F;´¯\u0095l\u0002\u0018ÇzuÌý\u0015U9\u0085\f×\u000BsÒ;ÏÖN\u0011=@õP\u0012\u0007ý]\u00ADYÕ9¨ã\u0015g\u001F\f÷´\u0018¶ ºÊ+\u0016ñ\\rÿÛòið%\u009D_\u0096o\u0084©ä\\r$jÅØrÛ\u001B2Ù=ÒõRÉ«\\\\Xð\u009E\u0090Í-,ô{¯\u0084F\u0084\u0087\u008A\u0005\u0002Q\u001BÅ\u009ENU\u0087(\u001EmZ\b\u008A©\u009B\u0016ø\u0011åÉ¨Må&ò\bf\u0092\u0092\u008E\u0089õ¦¯0\\'ð\u0003!Ìõûî\\'ÿhþð×\u008Då\u000FÐú\u0085= Ö·kî?þÚºõ\u0006Ù}\u0013@:\u008D\u0095ÕÆê\u0005÷íU÷Ò\u0005\u0004zõ\u000B\u0002ÚX¹J\\Z\u0093W¡µº\fB\u0085X§ñè6Ð&TÍ²63\u008FNÐ,szZ5\u0004²o¨\\nÃ\u0085â3Åáá=\u0098\\n\u0080l¬^%ØC\u001Cù\u001DàÕ\u0015\u0011E;ÆN\u0003\u0081\u0019j<ºà>zÏ}ë\u008A{ùÎÆë\u008FPãå\u0087,]îú9÷Ú»Ð\u0018\u0016\u0003\bÖp§Yþ\u008FíÈ\u0096\u0013è\u0014\u001Dµa\u0095JT¥¨@â\u0095JÁJ%it¯DT\u0017\u00928±6\t\u0014Õ\u0081²æû?Á\\\\ ÅY[\u0084Ê¬j\u0094\u0089î\u0010\u009De2\u0086ðÒIí\u008F-\u008AµA\u0016`a\u0081}óç¿T\u0018Q\u0017+üqã§÷Ð^-ö\u0017 Ø\u0082\u0015¬\u0017CDÛ~é|úÿ\u008E\u000F\u009Ex6}¼\u0090\u001D=±ëO¹gñÃà\u0002ùå½ïÊ¤\u008F«\u0007O HR\u0091yö©L>ç¨¶\u0093f\tÎd¶À\bK\u007FóãuwýÚæû77\u0096\u0097\u0093óB×#\u0081å\u0088Ð\u0099>\u009E;ñ§òÙâÐðb\\'\u0092\u0005æÏÖi¿}Î]ºÕXYj<ºâÞ¿Öüà>Ë\u0001Û\u0005Ë\\n[îñä½/ÆØÛQµ*[§b¬\u008ET\u0084l/\\nà¹PÅB!^W\u0004,1×îÒÅæw÷6\u001E¼¹ñóE2Ês\u0013ÓÖd\u000FvÈpxÄR_vd§nóüùÅ@\u0091ì8VZRÐR«\u0096%\u008FO¿À\u001Fh~\u009B\u008C\u0080Ù\u008CéA*JãþyC;\u0098\u0002\u0081\t\u008F}\u0080\u0089ÐÈÈ>\u0018ùý\fA\u0003\u0003Ás?\u001DõÔµÜÁ\u0091\bÈ½q\u0098â\tì¦ÔæW?5Öo4_ÿ¸Ã0\fë\u0092Q&Öf\u009C4QþA®@ä\u0018\u0011c\u009CAûÅñæìWw2f\u001F(1×q¦Ü\u009B\u0019\u009BNEµÂFÌ\u0017&1a¾\u00055à\bî\u0088ùF!<ã\\r\u0091\u0017\\\",f½ê÷i\u0018\u0018ð\u009Eúýuj\u0087ìÕCÌY«WØQkÍo/î¬\u00AD2\u0092ñ,5Xî½JÎRùÂ\u0090\bùÊX+åA\u0012s»}\u001BUÌjm\u009Fêà`\u0085³ÓhE¼\u00AD2Hø\u0096!!0\u0006\u001DEM\u008D:\u0096\u0018jØ\b\u0007\u00AD/\u0095\u0090Q.,ø¯\u0092Ä\u0098\f-Mæ-5ßz¯õñùÍ\u008B×\u0092Ù\u008A\u0010c,ñDÓ±FÌ& \u009D\u0081\u0002G¯\bA/\u0019_´z`\u0080>ìøè¢x¹Á\u0095DR½\u008F\\'FFÔ<\fÓªÊ:g]lQ\u00929\u0090\u0085§Æ\u0012Â\\Z\u0099ÿÂõ\u0081F¸\u0015Ç\f¯5fÈpû\u0015Ó\u0098Ñ¬*\u0084«ø\u0014©u÷\u001B÷\u009B[t[.\u0013\\r8\u0090«æË\u0082-#¡\u0004î|Þ\u0090«\u009ArD¶í9\u0088Ì=\\nøR\u0096\f/{±/Å§\u001Bâ\u00855\u0094\u0092\u0088Ê¼\fG©Í&@ÐrL$»ù$j\u0017\u0007Q¹8à\u0007Æâ ÷8(\u000Epa\u008A8È¾\u0086jÉ4\u00060ª¡\u0098eõ\u0095£/\\0\u00055¨1\u009C\u0018\\'4C{\\r;\\\\A\u001F~Ñ \u000BÅÕs5\tú÷¡¼Þùõ\u0012Úò\u0005\u0083\u0001\u0004SÇ\u0094vê\u0093\u0087É\fæw\t@.¯oÄ\\r_2(ìÊ\u0013Úbf\u000Fq0Z8èC\u0006ÕPÆ\u008E\u0004¨`_Y©\u0099¬<Ml!m2Rñhw¬yò@3Uf5\u0003\u0019r®\u0086rýÿçåÃ/\u0011`25àÌ#04s\u0086@ÂägN\u009FT\u0015\u0087Îr\u0004\u0082 \u0019\u0018\u0010úñS\u008Evï\u0083ÐH\u009ET¢D\u008Bq¯¢ÍF\u0098·\tÆ\u001Cý\u0088\u0083\u0012´?\\\\w$¿1ã\u0007Ð\u0099+\u0085}ü\u0014ÞÚì\u000B\\n\u0015´ë\u009AV=z\u0082Ê>Â\u0080\u0080YfÈ\u0016bþx{_ï]i<ºÝ¼ô\u008E{ù\u000EÝ\u0084\u008C\u0083\u008EgìWÚà\u008B\\'\u0089\u0015\u0097\u0097\u0002Û\u0081S\u0092\u0091L\u008EÚ~§L\u0012ýæwÑ$lp8\u008A\u009EÇ\u0091j¯Î\u000E\u0086ù«±\u0016±o¼>\\n\u0001#ÃaFÜ¥Ïágãµ»\u008D\u0095Û\u008DG7!¦o¬\\\\ÙxðÝãõ·\u009B\u001F~Üüî\u0003²òý\u009Ex|Fê<\u0014\u001B+\u0097[_½ßX]jÞx\u008FlI\u0093c\u001B\u007FK\\ZIà3ð\u0012¯¸KWÈnõowÄv\u0095ÆÞ°ÆýsF`móî\u000F\u009B\u001F}\u0002ÑDóö\u001DÂ;p\u008D¶§/ý\b\u007F\u0093Úß1ï£aÞÉ1\u009F»ôåæ¿nP\u000Bÿèu2\u0096\tû\u001Bçßo}·\u0086,dõóß1ãÅB·1\u0080÷ÁI\u009A\u0011\u0098;\f\u0003ðsY\u000B »Ë\u000F\u001F\u0090¤\u0098ß®,:Íæáò\u0088}À\u009FÈ\u0084xù^óÜy÷Ú?\u001BkKÍÛ_ºwÿå^¸Ù¼t\u009D-\\'ñQÿo{FäÙ\\\"¹X\u00AD\u0087çÝ\u009Fo\u000E\u0091c7÷\u009D«î\u0085+\u001BË\u001F\fmÜÿ\u0004~Ø9\u009EÃFÜÒEì\\\"ª\u0096eZÂ\u0098\u007FÜ\u0097\u0086\u0002\\Z¡!\u0097\u000EÞ\u0006\u0006\u0088çGjJ\u0010\u008AÙ\u008E\u0085\u0013j1\u0010\u0001\\rL\u0011Ayën\u001Bzý\u0083B\u009Fb\u0086T>òÂt\\\"W\u0018d¬\u008EqçÖ)\u0012·1¹\u00054lÍ\u008C§PØ¾8Þ×G²½É)\u0014\u007F é\u0099P\u009B\u008Diò)Æs8®-k6Nz \u0087KmwWº´á¶\u0096\u0093áï\u0006(EH\u0097ÂÁø\u0010\u0091NÐ\\\"Jzû6Ø?Æ\u009Fæ\u0081\bý4\u0083h¨\\r.ó41\u008Ata¨\u0098¡û=BÚkV\u0012\u008A\u009EM1D×,Ubs\u001FH%\u008Eµâ«\u0010éÓª\u0083É\u008C©&\u0081QL-õry\u008A\u0086ÛRDs\u0094ã(\\nWõD\u0011ÓØ÷»w\u0098\u0080pu{\u0091 \u00812\u008E\u001Cc¥\\\"\u001B³ê¾ù\u0003ü1(û\u0015ÜÖ\u00AD :Âz\u0019\\'¿Ühì}àô0&{\u0019a\u009Eh{8\u0084&\u001Fxbº\u008D²l\u0095Ñ6\u0085lx\u00AD¢\u0015qm\u008F¨V¤YP\u0016×â\u0095Z¤\u0081_\u0014À{\\rÌ¶ôÅÔDº3ã(4;\u0093hÆÐhÆ\u0013\u0099òöñ\u008Cz\u0015Uò\u008DøÂP+ö¬´±²ÚúbÕ\u009BêÐ>0\u009AV8E.,\u0084\u0014\u008B>\u007F\u000Bæ\u009D\u001DÎ\u000Fà\u000FDX?\t\u0011¸½]èLÜY{Ì¾AOLµÛ\u009CNâþù®ßös\u0004z§;qn@[êÛí»$¦c\u0016}à8Iüüv½/Ò\u0003\b2o\u0097JEê6ñvL>\\Zô\u008Eß.º\u0017/øÛax/ðyÝ\u0094C\u0012\u0014&\u0004¦*:¢3\u009E\u008DG\u001D\u001F4\u009AÄ\u0082ï\u0097Fêe~V\u001D\u0012Â3lÛE\\\"AË|~\u000B+Lxö\u008Fá ¨LH~\u0087Ó¹Nm9·\u0001O{\u0096\u008A\u000EGÚêiWh²ß\u0085\u000ED\u008Bþ\u009E.§a@\u0095\u0011&K!Íúóg Ó\u0014í\u0094©¤\u0018\u0017;(=ÔSÎ1\u009F×Î¨åôpæ·\\'æ\u001E\u0007@Ì\u009AÆ\u008F\u0080ÐÙ|ÀV¬BëºÓY§f\u0017¥R\u001C Kn\u0005\f\u0014\u0018C\u0013\u000Féé\u0093#/®\u0019¯Uôñw\u008Cb\u0017\u0099mXüì;\u009FU?V\u008Eq9¹\u0094Ó-ä\u009C\u0086\u0013\u0004º\u009D2$Î\u000F&Ä#Ú-yî¿\u0093\\\"ìÑ\u008C(èD4Tã\u008Cæq\u0094\u009B\u0084³Î\u000FÅ\u0089ü¿@y¼´C$RÚ\u0003Ò\u0083ôúß2õÌG\\0Tø\\'ëåYõ\\0\u009BCÅïe$OF\u008B$¡Bµ\u009F\u0082\u008AN7ã\u0012P»¥à°î¥\u009F\u009D\u0012r/\u0099ô\\0\u009Apú\u008Bd¹%¤º\u009Dÿ\u0013\\\"Ôß8ëA\u0018\u001Dr&x\u0019ø\u008A=\u001C$\u001CEÕ\u009A<O+È\u0015[Xð\u000F«9u&Ê§\\n\u0094ùæ\u0083ÆÚõ_O\u0099á\u0014°D\u0014oE\u0091ÉÒÊzQ#Uä~?\u0013*ªÇ$YR;\u009Fé\u0093$Ñg+\\\"ÜfòP\u009C\u0004Ûæ0\u009FRçëµtT¢èâ\u0099b\u0001\\'èá\u000Büô®\u0089ÐÞç`}^^\u0096\u009Eµõ\t?\u0011O\u008Ai\u008A8\u0094(\u0087R\u0098ÃÆÊ_\u009B×\u001F\u00924<ôÍÄõ\u008Bî×\u001F6ï^ )y@\u001B¼RÆ£\u009F\u00160òì©ãi½®¶íÙ½\u007FË½¶LÆ¯4\u0088ùË\fJa2\u0016é7X±¹\u008B½\u008A;>ù±«¨£9\u008F¿\u0082\u0098\u0013vºÃ\\\"\u008EIdÞ¢M÷.æ¸\u0004è_Ï\u009E\u007FUQ÷Eröqä6m\u009E\u0089LÈ½\u0086ñÞÞJo[Å½m_\u0087úè¸«\\0\u008AN´\u000B\u009D\t\f!É¶2ú¦nk\u009BÊ=}Ú\u0094\\na¦YÖ$ÊêÁ\u0095MîËÆ~\u007F\u0010ô×ëN.\u008D´Kí#üÁh\u0005ölvñXé¾pÔ#è¶Ý\u0011m\u0091>$;\u0095Ü\u008Cn\u009A\u0016j\u0002a=J1\u0017òøW&:B\u0093ØCÛ#\fn\u001B¢óÞJâ\u0013\u0097.\\r=?\u0001\\rõ¾àò\u009F\ttÍ\u0092w¡Ô´¬\u009C\u009AµÌºQÎâÛ\u008CÆ\fPÆ¸À\u0014ãIgìÉ\u0099§gFgfÐÕ6\u009AQ«{\u0017ÐT´rY5D|\u0093PÌ9\u0083\u0080n1\u008C¯Á\\'ï`m\u0093\u0013y\u008C°3bæ\u001C\\\"ÀÉ\u0016ö\u0086.8£\b°1e= 3ã°\u0099ÛB×F\u0088±U=¢\u008E\u008A1\\\\Ú\u0003ÂÐh§\bÃ¥= T¼\u0010ÿ\u0085²\u0087\u008D+ê\u0001\u0095ÿ=3Å\u0013¼G\u0091ôMôg³\u0002ù¤xóo\u0097ü/]ý\u000F\u008B£\u0017\u001C4?|0´ùÑ?ÈG¿ä\u0003`òý°\u0090Ív¥\u008A\\\\ûMèrðs\fM})ÿ\u0086.zÍ§jÁàK\u0005Åè~¯\\Z-ä\u0081UÃ)Òbt\u00993íÊVeK©<\u000F\u0005\u0084\u0018ö\u009DN\u0006øúÓ1aÏ\u009EBíÌx\bqÕ\u0090O\u0093¢\u0014ÇßtÝqLàÏ4\u0014]SN\u0001 ¿\u0097\u0094Î\u0088ô\u00064\u0011]×5U\u0018öYå®êðºWê\u0096\\rS\u008DP35`Â\\Zgä\u0091¬[K\u009EC}r}\u0080^Ü\u008B«;ÓGh¯\fõ%x½q×W\bøÐ^ä.\u009DhG\u0001ÌÙ5]\u009E\u001F\u0013ðÌ»%z\u0082Ý¯\bûÜµ\u001CÝH \u0014`uó\u0017kpD\u0091ëàè®2ý\u000BÛÉ$Ýµ\u009EpÐ\\\"äu\u0086î(ËÊº6k\u008C\tè\\\"PjX\u0004Ðò\u009Fá¥,`°\u0092\u0088À|b§aô«VvÚ\u0004æ«Y\\\\HeEk\u0010t\\\\¹\u0085o!\u008D©pÌ\\ZW\u001C\u0010\u0004\u007FHFßãõ[\u009Cè\u0011\u0013þ\u0018öÒÇ<I?\u000F\u0001Eqt´\u0010\u0088ÉCFN\u0005ûø×X\u009DzVD.Ð\\nTL¯Å@zMfÁX\u0094y§\u001C\b9\u001FH\u0019\u009E\u0091jèk Hf\u009C\u0083ï\u009F\\\\ô\u0089EÜNU\\\"o3Á\u001Bù·\u0013\u0002;\u0011\u0018\u001DA-\\n\u0085Ð\u001D\u008A%\u0011æ\u0014\u0081\\\\Ó\\\\\u0012G@\u0005ìÌ\u0002ìVBíXP\u0012=ñ@XZ¨\u0088ô\u001FP\u0080\u009C¦XÃEvË`\u0015ð$Z\u0012\u008B»GEÏ\u009E\u0015\u0015iJ\u009C$¶Åk\\'¾õ0zFêÀ\u009A¥Y\u008Dâ¤giþÍ\u0097\\\"{áFp¯d÷\u000E\u0010y\u0093mí¤#\u0083!¦ÈMu$ó½SÏ~9Ä\u0012a8:`\u0018R¶O\u0017{âÞ\u0091®6\u0003\u009D\u000BÜè\\ZÊ\u0097\u0099\u0006ºÛ\u0007z\u008CIè\u0082\u0001ë\u008FË@[\u0091D\u0083$*kÓ\u009EùòÕÇ\u0082¨\u0097-Un\u0003\u008C5NA:+\u009FE\\0\u001E¢Ø\u0083ÐýÓ\u008CäBWè¦CHÌÁ>\u0003K\u000ESJ\\'Í\\\"3_¶ÛÏ\u00800Ô3\u0089Çëo\u0013«\u0080IÞ\\'¯£\\r  :BZ ÿÐQSGÕ{{è[S<ÿÁ³\u008F\u0003]\u0018\u0011§ú`ï«7Å{é\u008F\u0089ÕÎ~b½uµ\u0087¾ô\u000FH\u0089SyïÊôÏ\u0081BÝÁ\u0002ÂiÒ;[Bkn\u008C\\\"¹£\u0090\u001EÕ\u0018óÕz{%\u0086v\u0089\u0093©\u0090U%«0\\Z_ù©«\u0089UÛZ[wß¹ä\u007FöÝº÷¨ùñÊÖu\u001C³\u008FáÓ\u0016*\u009F$}¡¯L¼Þ»+ÙÛUá»\u000B\u008DUzè\u0014?PÙs\u009Adêí8\u009EÂJ`\u0012\u0084\u0093Ïª++Ísç\u009Bç>ß®ô¹¯È\u0019r¼\u0012º\u008DE\u001BªeO\u0007;Ã\u007F\u008F+÷Ý\u0087\u00AD¿\u007F\u0093È£\u0098°ñ½ªüÔgF¦l\u0013¯\u0013x±Fá/þL\u0001Ù@dì\u00985\u009CäàÍ5|°\\Z\u0086\u000Ey\u009C¤m7\u009CÃìB\u0019\u0083\u0093_G\u0093á\u001CÙøþNó\u0083ûô\u000B\u00ADG\u007FÛüû\u0083\u0018ÌQ ¤ø\u009Fv¯]o>¸\u0004Ñ4\u0090\u0016G3S\u009D\u0014çnrb\u001E\u0087\\rW´Ç\u0093\\':g\\r¯\u009D\u0015¢í\u009Dv¡¦osÌÇIiò=\u001Bè5ÃaÅ\u009E*\u0013NDg jp¡{\t\u0004b\u0098áRo×\u0007\u008C\u0012\u009Fm\u0081³\u008F.\u00AD|I~)\u008D«2\u0019õ\u008CªÀ\\\\U\u0095Ñ¿\\\"P7Ê¦\u0004KRU>CN°Jâ\u001E|ay*`\u009A§\u0095\u000F\u008C\u001E¯_jÞþ²µö®ûÍ-\u0094\u0092óÁÍÖ\u0017«\u008F×ß\u008A\\n\u000EobùhÐ²Ä\u000F× ø\\Z\u0089\u008CÝNÁ-¹\b\u0082qÖè%\\n\u0089\u0082Û`\u0082\u000EÏÌ\u0095ò$&ÛW\fÃ\f\u001Bµµ\\r~ÑÑ\u0098Uå÷6¼\u0007ú{\\\"O0Mà\u007F oòÿ\u0001\u0098è\u001CYJp\\0\\0";
        String jy = unCompress(compressStr);
        System.out.println("\n解压缩后的字符串为--->" + jy);
        System.out.println("解压缩后的字符串长度为--->" + jy.length());

        System.out.println("\n压缩比例为" + len1 / len0);

        //判断
        if (str.equals(jy)) {
            System.out.println("先压缩再解压以后字符串和原来的是一模一样的");
        }
    }

    /**
     * 字符串的压缩
     *
     * @param str 待压缩的字符串
     * @return 返回压缩后的字符串
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将 b.length 个字节写入此输出流
        gzip.write(str.getBytes());
        gzip.close();
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("ISO-8859-1");
    }

    /**
     * 字符串的解压
     *
     * @param str 对字符串解压
     * @return 返回解压缩后的字符串
     * @throws IOException
     */
    public static String unCompress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
        ByteArrayInputStream in = new ByteArrayInputStream(str
                .getBytes("ISO-8859-1"));
        // 使用默认缓冲区大小创建新的输入流
        GZIPInputStream gzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n = 0;
        while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
            out.write(buffer, 0, n);
        }
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("UTF-8");
    }

    /**
     * 根据byte数组，生成文件
     *
     * @param bfile    byte数组
     * @param filePath 存储路径
     * @param fileName 文件名称
     * @return true:保存成功 false:保存失败
     */
    public static boolean getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        boolean result = true;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return result;
    }

}