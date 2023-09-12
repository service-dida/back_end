package com.service.dida.global.util.service;

import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftRequestDto.PostNftRequestDto;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.ErrorCode;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import com.service.dida.global.config.properties.KasProperties;
import com.service.dida.global.util.usecase.KasUseCase;
import com.service.dida.global.util.usecase.UtilUseCase;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KasService implements KasUseCase {

    private final KasProperties kasProperties;
    private final UtilUseCase utilUseCase;

    private String checkResponse(HttpResponse<String> response, String parameter,
        ErrorCode errorCode) throws BaseException, ParseException {
        if (response.statusCode() != 200) {
            throw new BaseException(errorCode);
        }
        JSONObject jsonObject = parseBody(response);
        if (jsonObject.get(parameter) == null) {
            throw new BaseException(errorCode);
        }
        return (String) jsonObject.get(parameter);
    }

    private String useKasApi(String query, String method, HttpRequest.BodyPublisher body,
        String parameter, ErrorCode errorCode)
        throws IOException, InterruptedException, BaseException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(java.net.URI.create(query))
            .header("Content-Type", "application/json")
            .header("x-chain-id", kasProperties.getVersion())
            .header("Authorization", kasProperties.getAuthorization())
            .method(method, body)
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            System.out.println(response.body());
        }
        return checkResponse(response, parameter, errorCode);
    }

    // 지갑 생성
    @Override
    public String createAccount()
        throws BaseException, IOException, ParseException, InterruptedException {
        String url = "https://wallet-api.klaytnapi.com/v2/account";
        return useKasApi(url, "POST", HttpRequest.BodyPublishers.noBody(), "address",
            WalletErrorCode.FAILED_CREATE_WALLET);
    }

    @Override
    public String uploadMetadata(PostNftRequestDto postNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        String url = "https://metadata-api.klaytnapi.com/v1/metadata";
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
            "{\n  " +
                "\"metadata\": {\n    " +
                "\"name\": \"" + postNftRequestDto.getTitle() + "\",\n    " +
                "\"description\": \"" + postNftRequestDto.getDescription() + "\",\n    " +
                "\"image\": \"" + postNftRequestDto.getImage() + "\"" +
                "\n  }\n" +
                "}");
        return useKasApi(url, "POST", body, "uri", NftErrorCode.FAILED_CREATE_METADATA);
    }

    @Override
    public String createNft(String address, String id, String uri)
        throws IOException, ParseException, InterruptedException {
        String url = "https://kip17-api.klaytnapi.com/v2/contract/" + kasProperties.getNftContract()
            + "/token";
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
            "{\n  " +
                "\"to\": \"" + address + "\",\n  " +
                "\"id\": \"" + id + "\",\n  " +
                "\"uri\": \"" + uri + "\"\n" +
                "}"
        );
        return useKasApi(url, "POST", body, "transactionHash", NftErrorCode.FAILED_CREATE_NFT);
    }

    @Override
    public String sendNftOutside(String sendAddress, String receiveAddress, String id)
        throws IOException, ParseException, InterruptedException {
        String url = "https://kip17-api.klaytnapi.com/v2/contract" + kasProperties.getNftContract()
            + "/token/" + id;
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
            "{\n  " +
                "\"sender\": \"" + sendAddress + "\",\n  " +
                "\"owner\": \"" + sendAddress + "\",\n  " +
                "\"to\": \"" + receiveAddress + "\"\n" +
                "}"
        );
        return useKasApi(url, "POST", body, "transactionHash", NftErrorCode.FAILED_SEND_NFT);
    }

    @Override
    public String sendKlayOutside(String sendAddress, String receiveAddress, double coin)
        throws IOException, ParseException, InterruptedException {
        return sendKlay(sendAddress,receiveAddress,coin);
    }

    @Override
    public double getKlay(Wallet wallet) throws IOException, ParseException, InterruptedException {
        String url = "https://node-api.klaytnapi.com/v1/klaytn";
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
            "{\n  " +
                "\"id\": 1,\n  " +
                "\"jsonrpc\": \"2.0\",\n " +
                " \"method\": \"klay_getBalance\",\n  " +
                "\"params\": [ \"" + wallet.getAddress() + "\",\"latest\"]\n" +
                "}"
        );
        String balance = useKasApi(url, "POST", body, "result", WalletErrorCode.FAILED_GET_KLAY);
        return Double.parseDouble(utilUseCase.pebToDecimal(balance));
    }

    @Override
    public double getDida(Wallet wallet) throws IOException, ParseException, InterruptedException {
        String url = "https://kip7-api.klaytnapi.com/v1/contract/" + kasProperties.getFtContract()
            + "/account/" + wallet.getAddress() + "/balance";
        String dida = useKasApi(url, "GET", HttpRequest.BodyPublishers.noBody(), "balance",
            WalletErrorCode.FAILED_GET_DIDA);
        return Double.parseDouble(utilUseCase.pebToDecimal(dida));
    }

    @Override
    public String mintDida(Wallet wallet, double coin)
        throws IOException, ParseException, InterruptedException {
        String url =
            "https://kip7-api.klaytnapi.com/v1/contract/" + kasProperties.getFtContract() + "/mint";
        String hexAmount = utilUseCase.decimalToPeb(coin);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
            "{\n  " +
                "\"from\": \"\",\n  " +
                "\"to\": \"" + wallet.getAddress() + "\",\n  " +
                "\"amount\": \"" + hexAmount + "\"\n" +
                "}"
        );
        return useKasApi(url, "POST", body, "transactionHash", WalletErrorCode.FAILED_MINT_DIDA);
    }

    @Override
    public String sendKlayToLiquidPool(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException {
        return sendKlay(sender.getAddress(), kasProperties.getLiquidPoolAccount(), coin);
    }

    @Override
    public String sendKlayToFeeAccount(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException {
        return sendKlay(sender.getAddress(), kasProperties.getFeeAccount(), coin);
    }

    @Override
    public String sendKlayFromLiquidPoolToUser(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException {
        return sendKlay(kasProperties.getLiquidPoolAccount(), sender.getAddress(), coin);
    }

    @Override
    public String sendDidaToLiquidPool(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException {
        return sendDida(sender, kasProperties.getLiquidPoolAccount(), coin);
    }

    @Override
    public String sendDidaToFeeAccount(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException {
        return sendDida(sender, kasProperties.getFeeAccount(), coin);
    }

    @Override
    public String sendDidaToSeller(Wallet sender, Wallet receiver, double coin)
        throws IOException, ParseException, InterruptedException {
        return sendDida(sender,receiver.getAddress(),coin);
    }

    @Override
    public String sendNft(Wallet sender, Wallet receiver, Nft nft)
        throws IOException, ParseException, InterruptedException {
        return sendNftOutside(sender.getAddress(),receiver.getAddress(),nft.getId());
    }

    private String sendDida(Wallet sender, String receiverAddress, double coin)
        throws IOException, ParseException, InterruptedException {
        String url = "https://kip7-api.klaytnapi.com/v1/contract/" + kasProperties.getFtContract()
            + "/transfer";
        String hexAmount = utilUseCase.decimalToPeb(coin);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
            "{\n  " +
                "\"from\": \"" + sender.getAddress() + "\",\n  " +
                "\"to\": \"" + receiverAddress + "\",\n  " +
                "\"amount\": \"" + hexAmount + "\"\n" +
                "}"
        );
        return useKasApi(url, "POST", body, "transactionHash", WalletErrorCode.FAILED_SEND_DIDA);
    }

    private String sendKlay(String senderAddress, String receiverAddress, double coin)
        throws IOException, ParseException, InterruptedException {
        String url = "https://wallet-api.klaytnapi.com/v2/tx/fd-user/value";
        String hexPay = utilUseCase.decimalToPeb(coin);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
            "{\n  " +
                "\"from\": \"" + senderAddress + "\",\n  " +
                "\"value\": \"" + hexPay + "\",\n  " +
                "\"to\": \"" + receiverAddress + "\",\n  " +
                "\"memo\": \"0x123\",\n  " +
                "\"nonce\": 0,\n  " +
                "\"gas\": 0,\n  " +
                "\"submit\": true,\n  " +
                "\"feePayer\": \"" + kasProperties.getFeePayerAccount() + "\"\n" +
                "}");
        return useKasApi(url, "POST", body, "transactionHash", WalletErrorCode.FAILED_SEND_KLAY);
    }

    private JSONObject parseBody(HttpResponse<String> response) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        return (JSONObject) obj;
    }
}
